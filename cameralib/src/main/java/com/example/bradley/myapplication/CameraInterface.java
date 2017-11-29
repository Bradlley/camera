package com.example.bradley.myapplication;

import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
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


    public void doStartPreview(SurfaceHolder surfaceHolder){

        try {
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.addCallbackBuffer(gBuffer);
            mCamera.setPreviewCallbackWithBuffer(this);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void doStartPreviewbyTextureView(SurfaceTexture texture){

        try {
            mCamera.setPreviewTexture(texture);
            mCamera.addCallbackBuffer(gBuffer);
            mCamera.setPreviewCallbackWithBuffer(this);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        mCamera.addCallbackBuffer(gBuffer);
    }


}
