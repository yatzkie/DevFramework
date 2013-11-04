package com.engine.framework.helper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class FileHelper {
	
	public static String TWO_HYPEN = "--";
	public static String BOUNDARY = "*****";
	public static String LINE_END = "\r\n";
	 
	public enum FileStatus {
		
		WRITE_SUCCESSFUL("Successfully saved file"),
		READ_SUCCESSFUL("Sucessfully read file"),
		SD_UNMOUNTED("SD card not mounted"),
		WRITE_FAILED("Failed to save file"),
		READ_FAILED("Failed to read file"),
		UNKNOWN_ERROR("Unexpected Error Occurred"), 
		NO_FILES("No files");
		
		String status = "";
		
		FileStatus(String status) {
			this.status = status;
		}
		
		public String toString() {
			return status;
		}
		
	}
	
	public static String getSDState() {
		return Environment.getExternalStorageState();
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
	
	public static FileStatus saveImage(String dirName, String fileName, byte[] data) {
		
		
		String sdState = Environment.getExternalStorageState();
		
		if(sdState.equals( Environment.MEDIA_MOUNTED) ) {
			
//			String sdCardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
			
			try {
				
				Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
				
//				File root = new File( sdCardDir );
				
				File dir = new File( dirName );
				
				if(!dir.exists())
					dir.mkdirs();
				
				File file = new File( dir, fileName );
				
				if(file.exists())
					file.delete();
				
				FileOutputStream fos = new FileOutputStream(file);
				bmp.compress(CompressFormat.PNG, 100, fos);
				fos.flush();
				fos.close();
				
				return FileStatus.WRITE_SUCCESSFUL;
			}
			catch(IOException e) {
				e.printStackTrace();
			}
				
			return FileStatus.WRITE_FAILED;
		}
		else
			return FileStatus.SD_UNMOUNTED;
	}
	
	public static FileStatus saveFile(String dirName, String fileName, byte[] data) {
		
		String sdState = Environment.getExternalStorageState();
		
		if(sdState.equals( Environment.MEDIA_MOUNTED) ) {
			
//			String sdCardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
			
			try {
				
//				File root = new File( sdCardDir );
				
				File dir = new File( dirName );
				
				if(!dir.exists())
					dir.mkdirs();
				
				File file = new File( dir, fileName );
				
				if(file.exists())
					file.delete();
				
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(data);
				fos.flush();
				fos.close();
				return FileStatus.WRITE_SUCCESSFUL;
			}
			catch(IOException e) {
				e.printStackTrace();
				
			}
			
			return FileStatus.WRITE_FAILED;
		}
		else
			return FileStatus.SD_UNMOUNTED;
	}
	
	public static FileStatus zipFile(String files[],String zipDirName, String zipFileName) {
		
		int BUFFER = 2048;
	
		try {
			
			File dir = new File( zipDirName );
			
			if(!dir.exists())
				dir.mkdirs();
			
			File file = new File( dir, zipFileName );
			
            BufferedInputStream origin = null;
            FileOutputStream dest = new FileOutputStream( file );
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                    dest));
            byte data[] = new byte[BUFFER];
 
            for (int i = 0; i < files.length; i++) {
            	
                System.out.println("Compress: " + "Adding: " + files[i]);
                FileInputStream fi = new FileInputStream(files[i]);
                origin = new BufferedInputStream(fi, BUFFER);
 
                ZipEntry entry = new ZipEntry(files[i].substring(files[i].lastIndexOf("/") + 1));
                out.putNextEntry(entry);
                int count;
 
                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                
                origin.close();
                
            }
 
            out.close();
            
            return FileStatus.WRITE_SUCCESSFUL;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return FileStatus.WRITE_FAILED;
		
	}

	public void unzip(String zipFile, String targetLocation) {
		 
        //create target location folder if not exist
        dirChecker(targetLocation);
        
        try {
        	
            FileInputStream fin = new FileInputStream(zipFile);
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {
 
                //create dir if required while unzipping
                if (ze.isDirectory()) {
                    dirChecker(ze.getName());
                } else {
                    FileOutputStream fout = new FileOutputStream(targetLocation + ze.getName());
                    for (int c = zin.read(); c != -1; c = zin.read()) {
                        fout.write(c);
                    }
 
                    zin.closeEntry();
                    fout.close();
                }
 
            }
            zin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	private void dirChecker(String dir) {
		// TODO Auto-generated method stub
		File file = new File( dir );
		if(file.exists()) file.mkdirs();
	}

	public static Bitmap scaleImage(Bitmap bitmap, int width, int height, int rotateAngle) {
		
		bitmap = Bitmap.createScaledBitmap( bitmap, width, height, false);
		Matrix matrix = new Matrix();
		matrix.postRotate( rotateAngle );
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
		return bitmap;
		
	}

	public static byte[] getByteArray(Bitmap src, Bitmap.CompressFormat format, int quality) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		src.compress(format, quality, os);
 
		return os.toByteArray();
		
	}
	
	public static void writeURLConnectionParam(DataOutputStream dos, String paramName, String value ) throws IOException {
		// TODO Auto-generated method stub
		 
		 dos.writeBytes( TWO_HYPEN + BOUNDARY + LINE_END );
         dos.writeBytes("Content-Disposition: form-data; name=\"" + paramName + "\"" + LINE_END);
         dos.writeBytes("Content-Type: text/plain; charset=UTF-8" + LINE_END);
         dos.writeBytes(LINE_END);
         dos.writeBytes(value + LINE_END);
         dos.flush();
	}

}
