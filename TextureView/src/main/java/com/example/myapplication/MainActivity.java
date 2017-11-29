package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;

import com.example.bradley.myapplication.CameraInterface;
import com.example.bradley.myapplication.CameraManager;

public class MainActivity extends AppCompatActivity implements CameraInterface.CamOpenOverCallback {

    private static final String TAG = "Bradlley";
    private CameraTextureView mTextureView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_main);

        mTextureView = new CameraTextureView(this);

        ViewGroup root = findViewById(R.id.root);
        root.addView(mTextureView);

        CameraManager.getInstance(this).setCallback(this);

    }

    @Override
    public void onCameraHasOpened() {
        CameraManager.getInstance(this).startPreviewBySurfaceTexture(mTextureView.getSurfaceTexture());
    }

    @Override
    public void onCameraopenFailed() {

    }
}
