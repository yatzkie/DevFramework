package com.engine.framework.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.engine.framework.enumerations.Method;
import com.engine.framework.services.WebServiceInfo;

public class WebServiceHelper {

	public static InputStream getWebServiceResponse(WebServiceInfo wsInfo) throws Exception {
		
		HttpClient client = new DefaultHttpClient();
		
		HttpResponse response = null;
		
		if(wsInfo.getMethod() == Method.GET) {
			
			HttpGet get = new HttpGet( wsInfo.getUrl() );
			
			response = client.execute( get );
			
		}
		else if(wsInfo.getMethod() == Method.POST) {
			
			HttpPost post = new HttpPost(wsInfo.getUrl());
			
			if(wsInfo.getParam() != null)
				post.setEntity( new UrlEncodedFormEntity( wsInfo.getParam() ) );
			
			response = client.execute( post );
			
		}
		
		if(response != null) {
			
			int statusCode = response.getStatusLine().getStatusCode();
			
			if(statusCode == HttpStatus.SC_OK)
				return response.getEntity().getContent();
			
		}
			
		return null;
		
	}

	public static boolean isNetworkAvailable(Context context) {
		
	    ConnectivityManager cm = (ConnectivityManager) 
	    		context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
	    
	    if (networkInfo != null && networkInfo.isConnected()) {
	        return true;
	    }
	    
	    return false;
	} 
}
