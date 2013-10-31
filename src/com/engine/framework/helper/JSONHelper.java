package com.engine.framework.helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONHelper {

	public static String getStringByIndex(String jsonString, int index) {
		
		try {
			
			return new JSONArray(jsonString).get(index).toString();
			
		} 
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
		
	}
	
	public static String getValueFromJSON(String jsonString, String key) {
		
		try {
			
			return new JSONObject(jsonString).getString(key);
			
		} 
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
}
