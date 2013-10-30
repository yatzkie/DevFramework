package com.engine.framework.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class FileHelper {
	
	
	public enum FileStatus {
		
		WRITE_SUCCESSFUL("Successfully saved file"),
		READ_SUCCESSFUL("Sucessfully read file"),
		SD_UNMOUNTED("SD card not mounted"),
		WRITE_FAILED("Failed to save file"),
		READ_FAILED("Failed to read file"),
		UNKNOWN_ERROR("Unexpected Error Occurred");
		
		String status = "";
		
		FileStatus(String status) {
			this.status = status;
		}
		
		public String toString() {
			return status;
		}
		
	}
	public static String getResponseString(InputStream source) throws IOException {

		String result = "";
		
		BufferedReader br = new BufferedReader( new InputStreamReader( source ) );
		
		String line = "";
		
		while( (line = br.readLine() ) != null )
			result+=line;
		
		br.close();
		
		return result;
		
	}
	
	public static FileStatus writeToSDCard(String dirName, String fileName, byte[] data) {
		
		String sdState = Environment.getExternalStorageState();
		
		if(sdState == Environment.MEDIA_MOUNTED) {
			
			String sdCardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
			
			try {
				
				File file = new File( sdCardDir, "/" + fileName);
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(data);
				fos.close();
				
				return FileStatus.WRITE_SUCCESSFUL;
			}
			catch(IOException e) {
				e.printStackTrace();
				return FileStatus.WRITE_FAILED;
			}
				
			
		}
		else
			return FileStatus.SD_UNMOUNTED;
		
	}
	
	public static FileStatus saveImageToSD(String dirName, String fileName, byte[] data) {
		
		
		String sdState = Environment.getExternalStorageState();
		
		if(sdState.equals( Environment.MEDIA_MOUNTED) ) {
			
			String sdCardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
			
			try {
				
				Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
				
				File root = new File( sdCardDir );
				
				File dir = new File( root, dirName );
				if(!dir.exists())
					dir.mkdirs();
				
				File file = new File( dir, fileName );
				
				if(file.exists())
					file.delete();
				
				FileOutputStream fos = new FileOutputStream(file);
				bmp.compress(CompressFormat.JPEG, 100, fos);
				fos.flush();
				fos.close();
				
				return FileStatus.WRITE_SUCCESSFUL;
			}
			catch(IOException e) {
				e.printStackTrace();
				return FileStatus.WRITE_FAILED;
			}
				
			
		}
		else
			return FileStatus.SD_UNMOUNTED;
	}
	
	public static String getDateFormat(String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat( format, Locale.US);
        String date = dateFormat.format(new Date());
        return date;
	}
}
