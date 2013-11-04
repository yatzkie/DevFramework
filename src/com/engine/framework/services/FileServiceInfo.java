package com.engine.framework.services;

import java.io.File;
import java.util.ArrayList;

import com.engine.framework.enumerations.FileType;

public class FileServiceInfo {
	

	private File mFile;
	private String[] files;
	private String zipDir;
	private String zipPath;
	private ArrayList<FileData> toSaveList;

	public void setFile(File file) {
		mFile = file;
	}
	
	public File getUploadFile() {
		return mFile;
	}
	
	public void setFiles(String... files) {
		this.files = files;
	}
	
	public String[] getFiles() {
		return files;
	}
	
	public void setZipFileName(String path){
		zipPath = path;
	}
	
	public String getZipFileName() {
		// TODO Auto-generated method stub
		return zipPath;
	}
	
	public void setZipDir(String path){
		zipDir = path;
	}
	
	public String getZipDir() {
		// TODO Auto-generated method stub
		return zipDir;
	}
	
	public void addFileToSave(String dir, String fileName, byte data[], FileType type) {
		
		if(toSaveList == null) toSaveList = new ArrayList<FileData>();
		
		toSaveList.add(new FileData(dir,fileName,data,type));
		
	}
	
	public ArrayList<FileData> getFilesToSave() {
		return toSaveList;
	}
	
}
