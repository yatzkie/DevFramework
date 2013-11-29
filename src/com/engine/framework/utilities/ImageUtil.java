package com.engine.framework.utilities;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class ImageUtil {

	public static Bitmap scaleImage(Bitmap bitmap, int width, int height, int rotateAngle) {
		
		bitmap = Bitmap.createScaledBitmap( bitmap, width, height, false);
		Matrix matrix = new Matrix();
		matrix.postRotate( rotateAngle );
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
		return bitmap;
		
	}

	public static Bitmap scaleImage(Bitmap bitmap, int width, int height) {
		
		bitmap = Bitmap.createScaledBitmap( bitmap, width, height, false);
		return bitmap;
		
	}

	public static Bitmap rotateBitmap(Bitmap bitmap, int rotateAngle) {
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
	

}
