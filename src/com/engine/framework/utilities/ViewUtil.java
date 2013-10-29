package com.engine.framework.utilities;

import android.widget.EditText;

public class ViewUtil {

	public static boolean isEmpty(EditText... editTexts) {
		
		for(EditText et : editTexts) {
			if(et.getText().toString().trim().length() == 0)
				return true;
		}
		
		return false;
	}
	
	
}
