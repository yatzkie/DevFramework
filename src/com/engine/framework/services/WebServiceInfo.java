/**
 * Author: Ronald Phillip C. Cui
 * Reference Author: Napolean A. Patague
 * Date: Oct 13, 2013
 */
package com.engine.framework.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.engine.framework.enumerations.Method;


public class WebServiceInfo {

	private String mUrl;
	private Method mMethod;
	private List<NameValuePair> mParam;
	private String mExtra;
	private File mUploadFile;
	private String[] files;
	private String zipPath;
	private String zipDir;
	
	public WebServiceInfo(String url, Method method) {
		mUrl = url;
		mMethod = method;
	}

	public WebServiceInfo setURL(String url) {
		mUrl = url;
		return this;
	}
	
	public String getUrl() {
		return mUrl;
	}
	
	public WebServiceInfo setMethod(Method method) {
		mMethod = method;
		return this;
	}
	
	public Method getMethod() {
		return mMethod;
	}
	
	public WebServiceInfo addParam(String name, String value) {
		
		if(mParam == null) mParam = new ArrayList<NameValuePair>();
		mParam.add(new BasicNameValuePair( name, value));
		
		return this;
	}
	
	public List<NameValuePair> getParam() {
		return mParam;
	}
	
	
	public WebServiceInfo setExtra(String extra) {
		mExtra = extra;
		return this;
	}
	
	public String getExtra() {
		return mExtra;
	}
	
	public void setUploadFile(File uploadFile) {
		mUploadFile = uploadFile;
	}
	
	public File getUploadFile() {
		return mUploadFile;
	}
	
	public void setUploadFiles(String... files) {
		this.files = files;
	}
	
	public String[] getUploadFiles() {
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
}
