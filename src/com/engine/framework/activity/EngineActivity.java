/**
 * Author: Ronald Phillip C. Cui
 * Date: Oct 24, 2013
 */
package com.engine.framework.activity;

import android.app.Activity;

public abstract class EngineActivity extends Activity {

	public abstract void onBackPressed();
	public abstract void onStart();
	public abstract void onPause();
	public abstract void onResume();
	public abstract void onDestroy();
	public abstract void init();
	
}
