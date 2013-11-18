package com.engine.framework.utilities;

import android.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;

public class DialogUtil {

	public enum DialogType {
		ALERT,
		CONFIRMATION,
		CUSTOM
	}
	
	public static final int OK = 1;
	public static final int CANCEL = 0;
	
	AlertDialog.Builder dialogBuilder;
	
	public static void showAlertDialog(Context context, String title, String message) {
		
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
			dialogBuilder.setTitle(title);
			dialogBuilder.setMessage(message);
			dialogBuilder.setCancelable(true);
			dialogBuilder.setPositiveButton("OK", new OnClickListener() {
	
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										dialog.dismiss();
									}
									   
						 });
			
			AlertDialog dialog = dialogBuilder.create();
			dialog.show();				   	
			
	}
	
	public static void showAlertDialog(Context context, String title, String message, OnDismissListener listener) {
		
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
		dialogBuilder.setTitle(title);
		dialogBuilder.setMessage(message);
		dialogBuilder.setCancelable(false);
		dialogBuilder.setPositiveButton("OK", new OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
								   
							   });
		
		AlertDialog dialog = dialogBuilder.create();
		dialog.setOnDismissListener(listener);
		dialog.show();				   	
		
	}
	
	public static void showConfirmationDialog(Context context, String title, String message, final OnClickListener listener) {
		
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
		dialogBuilder.setTitle(title);
		dialogBuilder.setMessage(message);
		dialogBuilder.setCancelable(false);
		dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				listener.onClick(dialog, OK);
			}
		} );
		dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				listener.onClick(dialog, CANCEL);
			}
		} );
		AlertDialog dialog = dialogBuilder.create();
		dialog.show();				   	
		
	}

	public static void showListDialog(Context context, String title, String[] values, OnClickListener listener) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
	    builder.setTitle(title)
	           .setItems(values, listener);
	    builder.setPositiveButton("Cancel", new OnClickListener() {
	    	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
			   
		   });
	    AlertDialog dialog = builder.create();
		dialog.show();	
	}
	
	public static Dialog getCustomDialog(Context context, int resId, String title) {
		
		Dialog dialog = new Dialog(context);
		dialog.setTitle(title);
		dialog.setContentView(resId);
		return dialog;
		
	}
}
