package com.taotao.tao_oat.camera;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @package com.taotao.tao_oat.camera
 * @file CameraPreview
 * @date 2019/4/11  10:59 AM
 * @autor wangxiongfeng
 */
public abstract class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private static final int MAX_UNSPECIFIED = -1;
    protected int mMaxHeight;
    protected int mMaxWidth;


    public static final int CAMERA_ID_ANY   = -1;
    public static final int CAMERA_ID_BACK  = 99;
    public static final int CAMERA_ID_FRONT = 98;
    public static final int RGBA = 1;
    public static final int GRAY = 2;

    protected int mCameraIndex = CAMERA_ID_ANY;


    public CameraPreview(Context context) {
        super(context);

        getHolder().addCallback(this);
        mMaxWidth = MAX_UNSPECIFIED;
        mMaxHeight = MAX_UNSPECIFIED;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    protected abstract boolean connectCamera(int width, int height);

}
