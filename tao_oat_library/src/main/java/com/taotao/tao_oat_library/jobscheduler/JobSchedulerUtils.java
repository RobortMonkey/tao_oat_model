package com.taotao.tao_oat_library.jobscheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Messenger;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;

import static com.taotao.tao_oat_library.jobscheduler.MyJobSchedulerService.MESSENGER_INTENT_KEY;

/**
 * @package com.taotao.tao_oat_library.jobscheduler
 * @file JobSchedulerUtils
 * @date 2019/3/27  5:03 PM
 * @autor wangxiongfeng
 */
public class JobSchedulerUtils {

    private static final String TAG = "JobSchedulerUtils";

    public static String WORK_DURATION_KEY = "work_duration_key";

    public static void BuildJobScheduler(int mJobId, ComponentName mServiceComponent, Context context,
                                         String delay, String deadline, String workDuration,
                                         boolean requiresUnmetered, boolean requiresAnyConnectivity, boolean leisure, boolean Charging) {

       //现对 jobId 对应的 job 进行配置   然后再调用服务开启
        JobInfo.Builder builder = new JobInfo.Builder(mJobId, mServiceComponent);

        if (!TextUtils.isEmpty(delay)) {
            //设置至少延迟多久后执行，单位毫秒.
            builder.setMinimumLatency(Long.valueOf(delay) * 1000);
        }
        if (!TextUtils.isEmpty(deadline)) {
            //设置最多延迟多久后执行，单位毫秒。
            builder.setOverrideDeadline(Long.valueOf(deadline) * 1000);
        }
        if (requiresUnmetered) {
            //设置需要的网络条件，有三个取值：
            //JobInfo.NETWORK_TYPE_NONE（无网络时执行，默认）、
            //JobInfo.NETWORK_TYPE_ANY（有网络时执行）、
            //JobInfo.NETWORK_TYPE_UNMETERED（网络无需付费时执行）
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
        } else if (requiresAnyConnectivity) {
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        }
        //是否在空闲时执行
        builder.setRequiresDeviceIdle(leisure);

        //是否在充电时执行
        builder.setRequiresCharging(Charging);

        // Extras, work duration.
        PersistableBundle extras = new PersistableBundle();
        if (TextUtils.isEmpty(workDuration)) {
            workDuration = "1";
        }
        extras.putLong(WORK_DURATION_KEY, Long.valueOf(workDuration) * 1000);

        builder.setExtras(extras);

        // Schedule job
        Log.d(TAG, "Scheduling job");
        JobScheduler tm = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.schedule(builder.build());


        Intent startServiceIntent = new Intent(context, MyJobSchedulerService.class);
        Messenger messengerIncoming = new Messenger(new IncomingMessageHandler());
        startServiceIntent.putExtra(MESSENGER_INTENT_KEY, messengerIncoming);
        context.startService(startServiceIntent);
    }
}
