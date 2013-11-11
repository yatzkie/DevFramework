package com.engine.framework.utilities;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.EditText;

public class ViewUtil {

	public static boolean isEmpty(EditText... editTexts) {
		
		for(EditText et : editTexts) {
			if(et != null && et.getText().toString().trim().length() == 0)
				return true;
		}
		
		return false;
		
	}
	
	public static boolean isInputEqualTo(EditText editText, double input) {
		
		if(Double.parseDouble( editText.getText().toString() ) == input)
			return true;
		
		return false;
		
	}
	
	public static int dpToPx(Context context, int dp) {
	    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
	    int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));       
	    return px;
	}
}
