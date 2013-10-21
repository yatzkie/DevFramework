/**
 * Author: Ronald Phillip C. Cui
 * Reference Author: Napolean A. Patague
 * Date: Oct 12, 2013
 */
package com.engine.framework.database;

import android.content.ContentValues;

public interface Table {
	
	public String getTableStructure();
	public String getName();
	
	public void insert(ContentValues values);
	public void update(ContentValues values, String filter);
	public void delete(ContentValues values, String filter);
	public void select();
	public void select(String filter);
	public void query(String query);
	
	
}
