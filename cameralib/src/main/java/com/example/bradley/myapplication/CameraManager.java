package com.example.bradley.myapplication;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.SurfaceHolder;

/**
 * Created by Bradlley on 2017/11/24.
 */

public class CameraManager  {

    private static CameraManager mInstance;
    private Context mContext;
    private CameraHandler mCameraHandler;
    private CameraInterface mCameraInterface;
    private static final int MSG_OPEN_CAMERA = 1;
    private CameraInterface.CamOpenOverCallback mCamOpenOverCallback;
    public static CameraManager getInstance(Context c){
        if(mInstance==null){
            synchronized (CameraManager.class){
                if(mInstance==null)
                    mInstance = new CameraManager(c);
            }
        }
        return mInstance;
    }

    private CameraManager(Context c){
        mContext = c;
        HandlerThread handlerThread = new HandlerThread("camera");
        handlerThread.start();
        mCameraHandler = new CameraHandler(handlerThread.getLooper());
        mCameraInterface = CameraInterface.getInstance();

    }


    private class CameraHandler extends Handler  {
        public CameraHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {


            super.handleMessage(msg);
            switch (msg.what){
                case MSG_OPEN_CAMERA:
                    mCameraInterface.doOpenCamera(mCamOpenOverCallback);
                    break;
            }
        }


    }

    public void setCallback(CameraInterface.CamOpenOverCallback call){
        mCamOpenOverCallback = call;
    }

    public void openCamera(){
        mCameraHandler.sendEmptyMessage(MSG_OPEN_CAMERA);
    }

    public void startPreview(SurfaceHolder surfaceHolder){
        mCameraInterface.doStartPreview(surfaceHolder);
    }

    public void closeCamera(){
        mCameraInterface.doStopCamera();
    }



}
