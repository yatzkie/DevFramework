package com.engine.framework.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;
import com.engine.framework.enumerations.ResponseStatus;
import com.engine.framework.helper.FileHelper;
import com.engine.framework.helper.FileHelper.FileStatus;
import com.engine.framework.services.interfaces.ServiceListener;
import com.engine.framework.services.response.Response;
import com.engine.framework.services.FileServiceInfo;

public class ZipFileService extends FileService {

	public ZipFileService(int requestId) {
		super(requestId);
	}
	
	public ZipFileService setProgressDialog(ProgressDialog dialog) {
		super.setProgressDialog(dialog);
		return this;
	}
	
	public ZipFileService setProgressDialog(Context context, String title, String message) {
		super.setProgressDialog(context, title, message);
		return this;
	}
	
	public ProgressDialog getProgressDialog() {
		return super.getProgressDialog();
	}
	
	public ZipFileService setServiceListener(ServiceListener listener) {
		super.setServiceListener(listener);
		return this;
	}
	
	public ServiceListener getServiceListener() {
		return super.getServiceListener();
	}
	
	@Override
	protected Response doInBackground(FileServiceInfo... params) {
		
		FileServiceInfo fsInfo = params[0];
		String files[] = fsInfo.getFiles();
		String zipFileName = fsInfo.getZipFileName();
		String zipDir = fsInfo.getZipDir();
		
		Response response = new Response();
		
		if(files == null || zipFileName == null) {	
			response.setStatus( ResponseStatus.FAILED, FileStatus.NO_FILES.toString() );
			return response;
		}
		
		if( !FileHelper.getSDState().equals(Environment.MEDIA_MOUNTED) ) {
			response.setStatus( ResponseStatus.FAILED, FileStatus.SD_UNMOUNTED.toString() );
			return response;
		}
		
		FileStatus status = FileHelper.zipFile( files, zipDir, zipFileName );
		
		if(status != FileStatus.WRITE_SUCCESSFUL) {
			response.setStatus( ResponseStatus.FAILED, FileStatus.NO_FILES.toString() );
			return response;
		}
		
		response.setStatus(ResponseStatus.SUCCESS, status.toString());
		
		return response;
	}


	
	
}
