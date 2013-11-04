/**
 * Author: Ronald Phillip C. Cui
 * Reference Author: Napolean A. Patague
 * Date: Oct 13, 2013
 */
package com.engine.framework.services.response;

import com.engine.framework.enumerations.ResponseStatus;

public class Response {

	private ResponseStatus mStatus = ResponseStatus.FAILED;
	private String mResult;
	private String mMessage;
	
	public void setStatus(ResponseStatus status) {
		mStatus = status;
		mMessage = status.toString();
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

	public void setStatus(ResponseStatus status, String message) {
		mStatus = status;
		mMessage = message;
	}
	
	public String getStatusMessage() {
		return mMessage;
	}
	
}
