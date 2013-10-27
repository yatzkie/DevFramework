/**
 * Author: Ronald Phillip C. Cui
 * Reference Author: Napolean A. Patague
 * Date: Oct 13, 2013
 */
package com.engine.framework.webservice;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpStatus;

import com.engine.framework.enumerations.ResponseStatus;
import com.engine.framework.helper.WebServiceHelper;
import com.engine.framework.webservice.interfaces.WebServiceListener;
import com.engine.framework.webservice.response.Response;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class FileUploadService extends AsyncTask<WebServiceInfo,Integer,Response> {

	public static final int NO_ID = -1;
	
	private WebServiceListener mListener;
	private ProgressDialog mDialog;
	private int mRequestId = WebService.NO_ID;

	public FileUploadService(int requestId) {
		mRequestId  = requestId;
	}
	
	public FileUploadService setProgressDialog(ProgressDialog dialog) {
		mDialog = dialog;
		return this;
	}
	
	public ProgressDialog getProgressDialog() {
		return mDialog;
	}
	
	public FileUploadService setWebServiceListener(WebServiceListener listener) {
		mListener = listener;
		return this;
	}
	
	public WebServiceListener getWebServiceListener() {
		return mListener;
	}
	
	@Override
	protected void onPreExecute() {
		if(mDialog != null) mDialog.show();
	}
	
	@Override
	protected Response doInBackground(WebServiceInfo... params) {
		
		WebServiceInfo wsInfo = params[0];
		File file = wsInfo.getUploadFile();
		String fileName = file.getName();
		Response response = new Response();
		
		if(file != null && file.isFile()) {
			
			HttpURLConnection conn = null;
			DataOutputStream dos = null;  
			String lineEnd = "\r\n";
			String twoHyphens = "--";
			String boundary = "*****";
			int bytesRead, bytesAvailable, bufferSize;
			byte[] buffer;
			int maxBufferSize = 1 * 1024 * 1024;
          
			try {
				
				 
                   FileInputStream fileInputStream = new FileInputStream( file );
                   conn = WebServiceHelper.uploadFile(wsInfo);
                   
                   if(conn != null) {
                	   
	                   dos = new DataOutputStream(conn.getOutputStream());
	          
	                   dos.writeBytes(twoHyphens + boundary + lineEnd); 
	                   dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
	                                             + fileName + "\"" + lineEnd);
	                    
	                   dos.writeBytes(lineEnd);
	          
	                   // create a buffer of  maximum size
	                   bytesAvailable = fileInputStream.available(); 
	          
	                   bufferSize = Math.min(bytesAvailable, maxBufferSize);
	                   buffer = new byte[bufferSize];
	          
	                   // read file and write it into form...
	                   bytesRead = fileInputStream.read(buffer, 0, bufferSize);  
	                      
	                   while (bytesRead > 0) {
	                        
	                     dos.write(buffer, 0, bufferSize);
	                     bytesAvailable = fileInputStream.available();
	                     bufferSize = Math.min(bytesAvailable, maxBufferSize);
	                     bytesRead = fileInputStream.read(buffer, 0, bufferSize);   
	                      
	                    }
	          
	                   // send multipart form data necesssary after file data...
	                   dos.writeBytes(lineEnd);
	                   dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
	          
	                   // Responses from the server (code and message)
	                   int serverResponseCode = conn.getResponseCode();
	                   String serverResponseMessage = conn.getResponseMessage();
	                   
	                   Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);  
	                   
	                   if( serverResponseCode == HttpStatus.SC_OK ){
	                	    response.setStatus(ResponseStatus.SUCCESS);
	                   }    
	                    
	                   //close the streams //
	                   fileInputStream.close();
	                   dos.flush();
	                   dos.close();
                   }	
			}
			catch(MalformedURLException e) {
				e.printStackTrace();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		response.setStatus(ResponseStatus.FAILED);
		
		return response;
	}
	
	@Override
	protected void onProgressUpdate(Integer... progress) {
		
		if( mListener != null ) mListener.onProgressUpdate(progress);
		
	}

	@Override 
	protected void onPostExecute(Response response) {
		
		if(mDialog != null) mDialog.dismiss();
		
		if( mListener != null ) mListener.webserviceCallback(response, mRequestId);
		
	}
	
}
