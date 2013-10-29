package com.engine.framework.utilities;

public class EngineAppInfo {

		public enum SERVER_TYPE {
			DEV,
			LIVE,
			BACKUP
		}
		
		private static final String DEV_URL = "";
		private static final String LIVE_URL = "";
		private static final String BACKUP_URL = "";
		
		public static String getServerURL(SERVER_TYPE type) {
			switch(type) {
			case DEV: return DEV_URL;
			case LIVE: return LIVE_URL;
			case BACKUP: return BACKUP_URL;
			default: return DEV_URL;
			}
		}
}
