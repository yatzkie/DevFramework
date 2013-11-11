package com.engine.framework.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {

	public static SharedPreferences getSharedPreferences(Context context, String preferenceName, int mode) {
		return context.getSharedPreferences( preferenceName , mode );
	}
	
}
