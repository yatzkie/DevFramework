package com.engine.framework.utilities;

import android.widget.EditText;

public class ViewUtil {

	public static boolean isEmpty(EditText... editTexts) {
		
		for(EditText et : editTexts) {
			if(et != null && et.getText().toString().trim().length() == 0)
				return true;
		}
		
		return false;
		
	}
	
	public static boolean isInputEqualTo(EditText editText, int input) {
		
		if(Double.parseDouble( editText.getText().toString() ) == input)
			return true;
		
		return false;
		
	}
	
}
