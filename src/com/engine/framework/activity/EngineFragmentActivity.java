package com.engine.framework.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public abstract class EngineFragmentActivity extends FragmentActivity {
		public abstract void showAlertDialog(String title, String message);
		public abstract void switchActivity(String packageName, String className, Bundle bundle);
}
