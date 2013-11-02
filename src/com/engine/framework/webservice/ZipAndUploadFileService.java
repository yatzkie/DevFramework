package com.engine.framework.webservice;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpStatus;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.engine.framework.enumerations.ResponseStatus;
import com.engine.framework.helper.FileHelper;
import com.engine.framework.helper.FileHelper.FileStatus;
import com.engine.framework.webservice.interfaces.WebServiceListener;
import com.engine.framework.webservice.response.Response;

public class ZipAndUploadFileService extends WebService {

	public ZipAndUploadFileService(int requestId) {
		super(requestId);
	}
	
	public ZipAndUploadFileService setProgressDialog(ProgressDialog dialog) {
		super.setProgressDialog(dialog);
		return this;
	}
	
	public ZipAndUploadFileService setProgressDialog(Context context, String title, String message) {
		super.setProgressDialog(context, title, message);
		return this;
	}
	
	public ProgressDialog getProgressDialog() {
		return super.getProgressDialog();
	}
	
	public ZipAndUploadFileService setWebServiceListener(WebServiceListener listener) {
		super.setWebServiceListener(listener);
		return this;
	}
	
	public WebServiceListener getWebServiceListener() {
		return super.getWebServiceListener();
	}
	
	@Override
	protected Response doInBackground(WebServiceInfo... params) {
		
		WebServiceInfo wsInfo = params[0];
		String files[] = wsInfo.getUploadFiles();
		String zipFileName = wsInfo.getZipFileName();
		
		Response response = new Response();
		
		if(files == null && zipFileName == null) {	
			response.setStatus( ResponseStatus.FAILED, FileStatus.NO_FILES.toString() );
			return response;
		}
		
		if( FileHelper.getSDState() != Environment.MEDIA_MOUNTED ) {
			response.setStatus( ResponseStatus.FAILED, FileStatus.SD_UNMOUNTED.toString() );
			return response;
		}
		
		FileStatus status = FileHelper.zipFile( files, zipFileName );
		
		if(status != FileStatus.WRITE_SUCCESSFUL) {
			response.setStatus( ResponseStatus.FAILED, FileStatus.NO_FILES.toString() );
			return response;
		}
		
		File file = new File(zipFileName);
		
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
                   URL url = new URL( wsInfo.getUrl() );
                    
                   // Open a HTTP  connection to  the URL
                   conn = (HttpURLConnection) url.openConnection(); 
                   conn.setDoInput(true); // Allow Inputs
                   conn.setDoOutput(true); // Allow Outputs
                   conn.setUseCaches(false); // Don't use a Cached Copy
                   conn.setRequestMethod("POST");
                   conn.setRequestProperty("Connection", "Keep-Alive");
                   conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                   conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                   conn.setRequestProperty("uploaded_file", zipFileName ); 
                    
                   dos = new DataOutputStream(conn.getOutputStream());
          
                   dos.writeBytes(twoHyphens + boundary + lineEnd); 
                   dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                                             + zipFileName + "\"" + lineEnd);
                    
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
                   
           	    	return response;
				
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
	
	
}
