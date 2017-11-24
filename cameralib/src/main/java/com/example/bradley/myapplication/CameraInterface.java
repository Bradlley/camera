package com.example.bradley.myapplication;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.util.List;

/**
 * Created by qianweili on 2017/11/24.
 */

public class CameraInterface implements Camera.PreviewCallback {

    private static CameraInterface mInstance;
    private Camera mCamera;
    public int previewWidth, previewHeight;
    public int screenWidth, screenHeight;
    private int bufferSize;
    public byte gBuffer[];



    public interface CamOpenOverCallback{
        void onCameraHasOpened();
        void onCameraopenFailed();

    }
    public static CameraInterface getInstance(){
        if (mInstance == null) {
            synchronized (CameraInterface.class){
                if (mInstance == null) {
                    mInstance = new CameraInterface();
                }
            }
        }
        return mInstance;
    }

    public void doOpenCamera(CamOpenOverCallback callback){
        try {
            mCamera = Camera.open();
            if (callback != null) {
                callback.onCameraHasOpened();
            }
        } catch (Exception e) {
            e.printStackTrace();
            callback.onCameraopenFailed();
        }
    }

    public void doStopCamera(){
        if(null != mCamera)
        {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    public void doStartPreview(){
        Camera.Parameters parameters = mCamera.getParameters();
        List<Camera.Size> preSize = parameters.getSupportedPreviewSizes();
        previewWidth = preSize.get(0).width;
        previewHeight = preSize.get(0).height;
        for (int i = 1; i < preSize.size(); i++) {
            double similarity = Math.abs(((double) preSize.get(i).height / screenHeight)
                     - ((double) preSize.get(i).width / screenWidth));
            if (similarity < Math.abs(((double) previewHeight / screenHeight)
                     - ((double) previewWidth / screenWidth))) {
                previewWidth = preSize.get(i).width;
                previewHeight = preSize.get(i).height;
               }
        }
        parameters.setPreviewSize(previewWidth, previewHeight);
        mCamera.setParameters(parameters);
        bufferSize = previewWidth * previewHeight;
        bufferSize  = bufferSize * ImageFormat.getBitsPerPixel(parameters.getPreviewFormat()) / 8;
        gBuffer = new byte[bufferSize];
        mCamera.addCallbackBuffer(gBuffer);
        mCamera.setPreviewCallbackWithBuffer(this);
        mCamera.startPreview();
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        mCamera.addCallbackBuffer(gBuffer);
    }





}
