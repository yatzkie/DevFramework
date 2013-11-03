package com.engine.framework.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.engine.framework.services.interfaces.ServiceListener;
import com.engine.framework.services.response.Response;

public abstract class FileService extends AsyncTask<FileServiceInfo,Integer,Response>{

	public ServiceListener mListener;
	public ProgressDialog mDialog;
	private int mRequestId;

	public FileService(int requestId) {
		mRequestId = requestId;
	}
	

	public FileService setProgressDialog(ProgressDialog dialog) {
		mDialog = dialog;
		return this;
	}
	
	public FileService setProgressDialog(Context context, String title, String message) {
		mDialog = new ProgressDialog(context);
		mDialog.setTitle(title);
		mDialog.setMessage(message);
		mDialog.setCancelable(false);
		mDialog.setIndeterminate(true);
		return this;
	}
	
	public ProgressDialog getProgressDialog() {
		return mDialog;
	}
	
	public FileService setServiceListener(ServiceListener listener) {
		mListener = listener;
		return this;
	}
	
	public ServiceListener getServiceListener() {
		return mListener;
	}
		

	@Override
	protected void onPreExecute() {
		if(mDialog != null) mDialog.show();
	}
	
	@Override
	protected abstract Response doInBackground(FileServiceInfo... params);

	@Override
	protected void onProgressUpdate(Integer... progress) {	
		if( mListener != null ) mListener.onProgressUpdate(progress);
	}

	@Override 
	protected void onPostExecute(Response response) {
		if(mDialog != null) mDialog.dismiss();
		if( mListener != null ) mListener.serviceCallback(response, mRequestId);
	}
	
}
