/**
 * Author: Ronald Phillip C. Cui
 * Date: Oct 24, 2013
 */
package com.engine.framework.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public abstract class EngineActivity extends Activity {

//	public abstract void showAlertDialog(String title, String message);
//	public abstract void switchActivity(String packageName, String className, Bundle bundle);
	
	public void startActivity(String packageName, String className, String bundleName, Bundle bundle) {

		Intent intent = new Intent();
		intent.setClassName(packageName, packageName + "." + className);
		if(bundleName != null && bundle != null) intent.putExtra( bundleName, bundle);
		
		startActivity(intent);
		
	}
	
	public void startActivityForResult(String packageName, String className, int requestCode, String bundleName, Bundle bundle) {
		
		Intent intent = new Intent();
		intent.setClassName(packageName, packageName + "." + className);
		if(bundleName != null && bundle != null) intent.putExtra( bundleName, bundle);
		
		startActivityForResult(intent, requestCode);
		
	}
}
