package com.engine.framework.services;

import java.util.ArrayList;

import com.engine.framework.enumerations.FileType;
import com.engine.framework.enumerations.ResponseStatus;
import com.engine.framework.helper.FileHelper;
import com.engine.framework.helper.FileHelper.FileStatus;
import com.engine.framework.services.response.Response;

public class SaveFileService extends FileService {

	public SaveFileService(int requestId) {
		super(requestId);
	}

	@Override
	protected Response doInBackground(FileServiceInfo... params) {
		// TODO Auto-generated method stub
		Response response = new Response();
		
		FileServiceInfo fsInfo = params[0];
		ArrayList<FileData> dataList = fsInfo.getFilesToSave();
		
		if(dataList != null && dataList.size() > 0) {
			
			FileStatus status = FileStatus.WRITE_FAILED;
			
			for(FileData fileData : dataList) {
				
				String dir = fileData.getDir();
				String fileName = fileData.getFileName();
				byte[] data = fileData.getData();
				
				if(fileData.getFileType() == FileType.IMAGE) {
					status = FileHelper.saveImage( dir, fileName, data);
				}
				else if(fileData.getFileType() == FileType.TEXT){
					status = FileHelper.saveFile( dir , fileName, data );
				}
				
				if(status != FileStatus.WRITE_SUCCESSFUL) {
					response.setStatus(ResponseStatus.FAILED, status.toString());
					return response;
				}
				
			}
			
			response.setStatus(ResponseStatus.SUCCESS, status.toString());
			
		}
		
		return response;
		
	}

}
