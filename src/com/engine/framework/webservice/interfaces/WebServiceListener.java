/**
 * Author: Ronald Phillip C. Cui
 * Reference Author: Napolean A. Patague
 * Date: Oct 13, 2013
 */
package com.engine.framework.webservice.interfaces;

import com.engine.framework.webservice.response.Response;

public interface WebServiceListener {
	public void onProgressUpdate(Integer... progress);
	public void webserviceCallback(Response response , int requestId);
}
