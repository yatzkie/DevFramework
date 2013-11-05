/**
 * Author: Ronald Phillip C. Cui
 * Reference Author: Napolean A. Patague
 * Date: Oct 13, 2013
 */
package com.engine.framework.services;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;

import com.engine.framework.enumerations.ResponseStatus;
import com.engine.framework.helper.FileHelper;
import com.engine.framework.helper.FileHelper.FileStatus;
import com.engine.framework.services.interfaces.ServiceListener;
import com.engine.framework.services.response.Response;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

public class FileUploadService extends WebService {

	public FileUploadService(int requestId) {
		super(requestId);
	}
	
	public FileUploadService setProgressDialog(ProgressDialog dialog) {
		super.setProgressDialog(dialog);
		return this;
	}
	
	public FileUploadService setProgressDialog(Context context, String title, String message) {
		super.setProgressDialog(context, title, message);
		return this;
	}

	
	public ProgressDialog getProgressDialog() {
		return super.getProgressDialog();
	}
	
	public FileUploadService setServiceListener(ServiceListener listener) {
		super.setServiceListener(listener);
		return this;
	}
	
	public ServiceListener getServiceListener() {
		return super.getServiceListener();
	}
	
	@Override
	protected Response doInBackground(WebServiceInfo... params) {
		
		WebServiceInfo wsInfo = params[0];
		File file = wsInfo.getUploadFile();
		
		Response response = new Response();
		
		if(file != null && file.isFile()) {
			
			HttpURLConnection conn = null;
			DataOutputStream dos = null;  
			String lineEnd = FileHelper.LINE_END; 
			String twoHyphens = FileHelper.TWO_HYPEN; 
			String boundary = FileHelper.BOUNDARY; 
			
			int bytesRead, bytesAvailable, bufferSize;
			byte[] buffer;
			int maxBufferSize = 1 * 1024 * 1024;
          
			try {
				
				 
                   URL url = new URL( wsInfo.getUrl() );
                    
                   // Open a HTTP  connection to  the URL
                   conn = (HttpURLConnection) url.openConnection(); 
                   conn.setDoOutput(true); // Allow Outputs
                   conn.setRequestMethod(wsInfo.getMethod().toString());
                   conn.setRequestProperty("Connection", "Keep-Alive");
                   conn.setRequestProperty("Cache-Control", "no-cache");
                   conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                  
                   dos = new DataOutputStream(conn.getOutputStream());
                  
                   if(wsInfo.getParam() != null) {
                	   for(NameValuePair param : wsInfo.getParam()) {
                		   FileHelper.writeURLConnectionParam(dos, param.getName(), param.getValue());
                	   }
                   }
                   
                   
                   dos.writeBytes(twoHyphens + boundary + lineEnd); 
                   dos.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\""
                                             + file.getName() + "\"" + lineEnd);
                   dos.writeBytes("Content-Type: " + URLConnection.guessContentTypeFromName(file.getName()) + lineEnd);
                   dos.writeBytes("Content-Transfer-Encoding: binary" + lineEnd);
                   
                   dos.writeBytes(lineEnd);
          
                   dos.flush();
                   
                   FileInputStream fileInputStream = new FileInputStream( file );
                   
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
	                     publishProgress(bytesRead);
	                     
                    }
          
                   // send multipart form data necesssary after file data...
                   dos.writeBytes(lineEnd);
                   dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
          
                   // Responses from the server (code and message)
                   int serverResponseCode = conn.getResponseCode();
                   String serverResponseMessage = conn.getResponseMessage();
                   response.setResult( FileHelper.getResponseString( conn.getInputStream()) );
                   
                   Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);  
                   
                   if( serverResponseCode == HttpStatus.SC_OK ){
                	    response.setStatus(ResponseStatus.SUCCESS);
                   }    
                    
                   //close the streams //
                   fileInputStream.close();
                   dos.flush();
                   dos.close();
                   conn.disconnect();
                   
           	    	return response;
				
			}
			catch(MalformedURLException e) {
				e.printStackTrace();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		response.setStatus(ResponseStatus.FAILED, FileStatus.NO_FILES.toString() );
		
		return response;
	}
	
	
}
