package com.engine.framework.services.interfaces;

import com.engine.framework.services.response.Response;

public interface ServiceListener {
	public void onProgressUpdate(Integer... progress);
	public void serviceCallback(Response response , int requestId);
}
