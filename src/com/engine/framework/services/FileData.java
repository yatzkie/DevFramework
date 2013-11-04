package com.engine.framework.services;

import com.engine.framework.enumerations.FileType;

public class FileData {

	private String mDir;
	private String mFileName;
	private FileType mType;
	private byte[] mData;

	public FileData(String dir, String fileName, byte data[], FileType type) {
		mDir = dir;
		mFileName = fileName;
		mData = data;
		mType = type;
	}
	
	public String getDir() {
		return mDir;
	}
	
	public String getFileName() {
		return mFileName;
	}
	
	public byte[] getData() {
		return mData;
	}
	
	public FileType getFileType() {
		return mType;
	}
}
