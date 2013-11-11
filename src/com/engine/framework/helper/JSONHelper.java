package com.engine.framework.helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONHelper {

	public static String getStringByIndex(String jsonString, int index) {
		
		try {
			
			return jsonString != null ? new JSONArray(jsonString).get(index).toString() : "Unexpected Error Occured";
			
		} 
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
		
	}
	
	public static String getValueFromJSON(String jsonString, String key) {
		
		try {
			
			return jsonString != null ? new JSONObject(jsonString).getString(key) : "Unexpected Error Occured";
			
		} 
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
		
	}
}
