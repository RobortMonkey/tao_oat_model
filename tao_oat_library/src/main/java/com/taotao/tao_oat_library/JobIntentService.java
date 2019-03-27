package com.taotao.tao_oat_library;

import android.Manifest;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobWorkItem;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.HashMap;

import static com.taotao.tao_oat_library.BuildConfig.DEBUG;

/**
 * @package com.taotao.tao_oat_library
 * @file JobIntentService
 * @date 2019/3/27  4:35 PM
 * @autor wangxiongfeng
 * <p>
 * 单进程service
 */
public abstract class JobIntentService extends Service {

    private static final String TAG = "JobIntentService";
    static final HashMap<ComponentName, WorkEnqueuer> sClassWorkEnqueuer = new HashMap<>();
    static final Object sLock = new Object();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    abstract static class WorkEnqueuer {
        final ComponentName mComponentName;

        boolean mHasJobId;
        int mJobId;

        WorkEnqueuer(Context context, ComponentName cn) {
            mComponentName = cn;
        }

        void ensureJobId(int jobId) {
            if (!mHasJobId) {
                mHasJobId = true;
                mJobId = jobId;
            } else if (mJobId != jobId) {

                throw new IllegalArgumentException("Given job ID " + jobId
                        + " is different than previous " + mJobId);
            }
        }

        abstract void enqueueWork(Intent work);

        public void serviceStartReceived() {
        }

        public void serviceProcessingStarted() {
        }

        public void serviceProcessingFinished() {
        }
    }


    public static void enqueueWork(@NonNull Context context, @NonNull Class cls, int jobId,
                                   @NonNull Intent work) {
        enqueueWork(context, new ComponentName(context, cls), jobId, work);
    }

    public static void enqueueWork(@NonNull Context context, @NonNull ComponentName component,
                                   int jobId, @NonNull Intent work) {
        if (work == null) {
            throw new IllegalArgumentException("work must not be null");
        }
        synchronized (sLock) {
            WorkEnqueuer we = getWorkEnqueuer(context, component, true, jobId);
            we.ensureJobId(jobId);
            we.enqueueWork(work);
        }
    }

    static WorkEnqueuer getWorkEnqueuer(Context context, ComponentName cn, boolean hasJobId,
                                        int jobId) {
        WorkEnqueuer we = sClassWorkEnqueuer.get(cn);
        if (we == null) {
            if (Build.VERSION.SDK_INT >= 26) { //android 8.0
                if (!hasJobId) {
                    throw new IllegalArgumentException("Can't be here without a job id");
                }
                //使用 JobScheduler 实现
                we = new JobWorkEnqueuer(context, cn, jobId);
            } else {
                //通过持有设备唤醒锁来保持服务运行
                we = new CompatWorkEnqueuer(context, cn);
            }
            sClassWorkEnqueuer.put(cn, we);
        }
        return we;
    }

    @RequiresApi(26)
    static final class JobWorkEnqueuer extends JobIntentService.WorkEnqueuer {
        private final JobInfo mJobInfo;
        private final JobScheduler mJobScheduler;

        JobWorkEnqueuer(Context context, ComponentName cn, int jobId) {
            super(context, cn);
            ensureJobId(jobId);
            JobInfo.Builder b = new JobInfo.Builder(jobId, mComponentName);
            mJobInfo = b.setOverrideDeadline(0).build();
            mJobScheduler = (JobScheduler) context.getApplicationContext().getSystemService(
                    Context.JOB_SCHEDULER_SERVICE);
        }

        @Override
        void enqueueWork(Intent work) {
            if (DEBUG) Log.d(TAG, "Enqueueing work: " + work);
            mJobScheduler.enqueue(mJobInfo, new JobWorkItem(work));
        }
    }


    static final class CompatWorkEnqueuer extends WorkEnqueuer {
        private final Context mContext;

        private final PowerManager.WakeLock mLaunchWakeLock;
        private final PowerManager.WakeLock mRunWakeLock;
        boolean mLaunchingService;
        boolean mServiceProcessing;

        CompatWorkEnqueuer(Context context, ComponentName cn) {
            super(context, cn);
            mContext = context.getApplicationContext();
            // PowerManager.WakeLock 保持 CPU 运转防止设备休眠的方式.
            if (mContext.checkPermission(Manifest.permission.WAKE_LOCK, Process.myPid(), Process.myUid()) == PackageManager.PERMISSION_GRANTED) {
                // Make wake locks.  We need two, because the launch wake lock wants to have
                // a timeout, and the system does not do the right thing if you mix timeout and
                // non timeout (or even changing the timeout duration) in one wake lock.
                PowerManager pm = ((PowerManager) context.getSystemService(Context.POWER_SERVICE));
                mLaunchWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, cn.getClassName() + ":launch");
                mLaunchWakeLock.setReferenceCounted(false);
                mRunWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, cn.getClassName() + ":run");
                mRunWakeLock.setReferenceCounted(false);
            } else {
                Log.w(TAG, "it would be better to grant WAKE_LOCK permission to your app so that tinker can use WakeLock to keep system awake.");
                mLaunchWakeLock = mRunWakeLock = null;
            }
        }

        @Override
        void enqueueWork(Intent work) {
            Intent intent = new Intent(work);
            intent.setComponent(mComponentName);
            if (DEBUG) Log.d(TAG, "Starting service for work: " + work);
            if (mContext.startService(intent) != null) {
                synchronized (this) {
                    if (!mLaunchingService) {
                        mLaunchingService = true;
                        if (!mServiceProcessing && mLaunchWakeLock != null) {
                            // If the service is not already holding the wake lock for
                            // itself, acquire it now to keep the system running until
                            // we get this work dispatched.  We use a timeout here to
                            // protect against whatever problem may cause it to not get
                            // the work.
                            mLaunchWakeLock.acquire(60 * 1000);
                        }
                    }
                }
            }
        }

        @Override
        public void serviceStartReceived() {
            synchronized (this) {
                // Once we have started processing work, we can count whatever last
                // enqueueWork() that happened as handled.
                mLaunchingService = false;
            }
        }

        @Override
        public void serviceProcessingStarted() {
            synchronized (this) {
                // We hold the wake lock as long as the service is processing commands.
                if (!mServiceProcessing) {
                    mServiceProcessing = true;
                    // Keep the device awake, but only for at most 10 minutes at a time
                    // (Similar to JobScheduler.)
                    if (mRunWakeLock != null) {
                        mRunWakeLock.acquire(10 * 60 * 1000L);
                    }
                    if (mLaunchWakeLock != null) {
                        mLaunchWakeLock.release();
                    }
                }
            }
        }

        @Override
        public void serviceProcessingFinished() {
            synchronized (this) {
                if (mServiceProcessing) {
                    // If we are transitioning back to a wakelock with a timeout, do the same
                    // as if we had enqueued work without the service running.
                    if (mLaunchingService && mLaunchWakeLock != null) {
                        mLaunchWakeLock.acquire(60 * 1000);
                    }
                    mServiceProcessing = false;
                    if (mRunWakeLock != null) {
                        mRunWakeLock.release();
                    }
                }
            }
        }
    }


}
