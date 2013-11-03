package com.engine.framework.webservice;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

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
		String zipDir = wsInfo.getZipDir();
		
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
		
		String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		File file = new File( sdPath + "/" + zipDir + "/" + zipFileName );
		
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
                  
                   FileHelper.writeURLConnectionParam(dos, "app_name", "my_value");
                  
                   dos.writeBytes(twoHyphens + boundary + lineEnd); 
                   dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
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
                   response.setResult( FileHelper.getResponseString( conn.getInputStream()));
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
		
		response.setStatus(ResponseStatus.FAILED);
		
		return response;
	}
	
	
}
