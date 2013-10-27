/**
 * Author: Ronald Phillip C. Cui
 * Reference Author: Napolean A. Patague
 * Date: Oct 13, 2013
 */
package com.engine.framework.enumerations;

public enum Method {
		POST,
		GET;
		
		public String toString() {
			switch(this) {
				case POST: return "POST";
				case GET: return "GET";
				default: return "";
			}
		}
}
