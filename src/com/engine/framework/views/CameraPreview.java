/* 
 *	Created by Ronald Phillip C. Cui Oct 29, 2013
 *	Copyright (c) 2013 Dev-Touch Information Systems. All rights reserved.
 */
package com.engine.framework.views;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.os.Build;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    private Camera mCamera;
	private boolean mPreviewRunning = false;
	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public CameraPreview(Context context, Camera camera) {
		super(context);
		// TODO Auto-generated constructor stub
		
		mCamera = camera;
		mCamera.setDisplayOrientation(90);
		initCamera();
	}
	
	private void initCamera() {

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        
        // deprecated setting, but required on Android versions prior to 3.0
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
            getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        
	}
	
	/* (non-Javadoc)
	 * @see android.view.SurfaceHolder.Callback#surfaceChanged(android.view.SurfaceHolder, int, int, int)
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub
		
		if (mPreviewRunning ) {

			mCamera.stopPreview();

		}
		
		Camera.Parameters p = mCamera.getParameters();

		p.set("orientation", "portrait");

		try {

			mCamera.setPreviewDisplay(holder);

		} catch (IOException e) {

			e.printStackTrace();

		}

		mCamera.startPreview();

		mPreviewRunning = true;

	}

	/* (non-Javadoc)
	 * @see android.view.SurfaceHolder.Callback#surfaceCreated(android.view.SurfaceHolder)
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		//open camera
		
	}

	/* (non-Javadoc)
	 * @see android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view.SurfaceHolder)
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
		if(mCamera != null) {
			mCamera.stopPreview();
			mPreviewRunning = false;
			mCamera.release();
		}
	}


	
}
