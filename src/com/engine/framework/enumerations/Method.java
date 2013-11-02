/**
 * Author: Ronald Phillip C. Cui
 * Reference Author: Napolean A. Patague
 * Date: Oct 13, 2013
 */
package com.engine.framework.enumerations;

public enum Method {
		POST("POST"),
		GET("GET");
		
		private String method;
		
		Method(String method) {
			this.method = method;
		}
		
		public String toString() {
			return method;
		}
}
