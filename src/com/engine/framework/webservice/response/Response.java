/**
 * Author: Ronald Phillip C. Cui
 * Reference Author: Napolean A. Patague
 * Date: Oct 13, 2013
 */
package com.engine.framework.webservice.response;

import com.engine.framework.enumerations.ResponseStatus;

public class Response {

	private ResponseStatus mStatus = ResponseStatus.FAILED;
	private String mResult;
	
	public void setStatus(ResponseStatus status) {
		mStatus = status;
	}
	
	public ResponseStatus getStatus() {
		return mStatus;
	}
	
	public Response setResult(String result) {
		mResult = result;
		return this;
	}
	
	public String getResult() {
		return mResult;
	}
	
}
