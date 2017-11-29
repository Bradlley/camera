package com.example.myapplication;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;

import com.example.bradley.myapplication.CameraManager;

/**
 * 文 件 名: CameraTextureView
 * 创 建 人: Bradley
 * 创建日期: 2017/11/29 21:25
 * 修改时间：
 * 修改备注：
 */
public class CameraTextureView extends TextureView implements TextureView.SurfaceTextureListener {

    private static final String TAG = "Bradlley";
    private Context mContext;
    private SurfaceTexture mSurface;

    public CameraTextureView(Context context) {
        super(context);
        Log.d(TAG, "CameraTextureView: ");
        mContext = context;
        this.setSurfaceTextureListener(this);
    }

    public CameraTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "CameraTextureView: ");
        mContext = context;
        this.setSurfaceTextureListener(this);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        Log.d(TAG, "onSurfaceTextureAvailable: ");
        mSurface = surfaceTexture;
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
        Log.d(TAG, "onSurfaceTextureSizeChanged: ");
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        Log.d(TAG, "onSurfaceTextureDestroyed: ");
        CameraManager.getInstance(mContext).closeCamera();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        Log.d(TAG, "onSurfaceTextureUpdated: ");
    }

    public SurfaceTexture _getSurfaceTexture(){
        return mSurface;
    }
}
