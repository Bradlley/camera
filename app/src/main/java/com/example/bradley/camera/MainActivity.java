package com.example.bradley.camera;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.bradley.myapplication.CameraInterface;
import com.example.bradley.myapplication.CameraManager;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback, CameraInterface.CamOpenOverCallback {

    private SurfaceView mSurfaceView;
    private CameraManager mCameraManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        mSurfaceView.getHolder().addCallback(this);

        mCameraManager = CameraManager.getInstance(this);
        mCameraManager.setCallback(this);


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mCameraManager.openCamera();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCameraManager.closeCamera();
    }

    @Override
    public void onCameraHasOpened() {
        mCameraManager.startPreview(mSurfaceView.getHolder());
    }

    @Override
    public void onCameraopenFailed() {

    }
}
