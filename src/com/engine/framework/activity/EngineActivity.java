/**
 * Author: Ronald Phillip C. Cui
 * Date: Oct 24, 2013
 */
package com.engine.framework.activity;

import android.app.Activity;
import android.os.Bundle;

public abstract class EngineActivity extends Activity {

	public abstract void showAlertDialog(String title, String message);
	public abstract void switchActivity(String packageName, String className, Bundle bundle);
}
