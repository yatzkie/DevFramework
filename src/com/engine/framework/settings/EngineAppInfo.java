package com.engine.framework.settings;

import java.util.Random;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;

import com.engine.framework.enumerations.ServerType;

public abstract class EngineAppInfo {
	
	/* Application Configurations */
	private ServerType APP_MODE = ServerType.DEV;

	/* slash screen timeout config */
	private long TIMEOUT = 3000;
	
	/* package where the activities are located */
	private String PACKAGE_NAME;

	/* name of the shared preference of the app */
	private String SHARED_PREF_NAME;
	
	/* Server Urls */
	private String DEV_URL;
	private String LIVE_URL;
	private String BACKUP_URL;
	
	/* Getter and Setter for timeout ex. Splash Screen */
	public void setTimeout(long timeout) {
		TIMEOUT = timeout;
	}
	
	public long getTimeout() {
		return TIMEOUT;
	}
	
	/* Getter and Setter for App Mode */
	public void setAppMode(ServerType appMode) {
		APP_MODE = appMode;
	}
	
	public ServerType getAppMode() {
		return APP_MODE;
	}
	
	/* Getter and Setter for Base URL */
	public void setBaseUrl(ServerType serverType, String url) {
		switch(APP_MODE) {
			case LIVE: LIVE_URL = url; break;
			case BACKUP: BACKUP_URL = url; break;
			case DEV: default: DEV_URL = url; break;
		}
	}
	
	private String getBaseUrl() {
		switch(APP_MODE) {
		case DEV: return DEV_URL;
		case LIVE: return LIVE_URL;
		case BACKUP: return BACKUP_URL;
		default: return DEV_URL;
		}
	}
	
	public String getUrl(String function) {
		return getBaseUrl().concat(function);
	}
	
	/* Setter and Getter for Shared Preferences */
	public void setPreferenceName(String prefName) {
		SHARED_PREF_NAME = prefName;
	}
	
	public String getPreferenceName() {
		return SHARED_PREF_NAME;
	}
	
	public SharedPreferences getSharedPreferences(Context context) {
		return context.getSharedPreferences( getPreferenceName(), Context.MODE_PRIVATE );
	}
	
	/* Setter and Getter for Package Name */
	public void setPackageName(String packageName) {
		PACKAGE_NAME = packageName;
	}
	
	public String getPackageName() {
		return PACKAGE_NAME;
	}
	
	public String getDeviceId(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}
	
	public String generateCode(int length) {
		// TODO Auto-generated method stub
		
		String codelist = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		String code = "";
		for(int i =1; i <= length; i++) {
			int count = random.nextInt(codelist.length() - 1);
			code = code.concat("" + codelist.charAt(count) );
		}
		return code;
	}
}
