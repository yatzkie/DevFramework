/**
 * Author: Ronald Phillip C. Cui
 * Reference Author: Napolean A. Patague
 * Date: Oct 12, 2013
 */
package com.engine.framework.database;

import com.engine.framework.helper.DatabaseHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class Table {
	
	private SQLiteDatabase db;
	
	public abstract String getTableStructure();
	public abstract String getName();
	
	public long insert(ContentValues values) {
		db = DatabaseHelper.getInstance().getWritableDatabase();
		if(db != null) return db.insert( getName(), null, values);
		return -1;

	}
	
	public int update(ContentValues values, String filter) {
		db = DatabaseHelper.getInstance().getWritableDatabase();
		if(db != null) return db.update( getName(), values, filter, null);
		return -1;
	}
	
	public int update(ContentValues values, String whereClause, String[] filterValues) {
		db = DatabaseHelper.getInstance().getWritableDatabase();
		if(db != null) return db.update( getName(), values, whereClause, filterValues );
		return -1;
	}
	
	public int delete() {
		db = DatabaseHelper.getInstance().getWritableDatabase();
		if(db != null) return db.delete( getName(), null, null);
		return -1;
	}
	
	public int delete(String whereClause, String[] filterValues) {
		db = DatabaseHelper.getInstance().getWritableDatabase();
		if(db != null) return db.delete( getName(), whereClause, filterValues);
		return -1;
	}
	
	public Cursor select() {
		db = DatabaseHelper.getInstance().getReadableDatabase();
		if(db != null) return db.rawQuery( "SELECT * FROM " + getName(), null);
		return null;
	}
	
	public Cursor select(String filter,String[] filterValues) {
		db = DatabaseHelper.getInstance().getReadableDatabase();
		if(db != null) return db.rawQuery( "SELECT * FROM " + getName() + " where " + filter, filterValues );
		return null;
	}
	
	public Cursor rawQuery(String query, String[] filterValues) {
		db = DatabaseHelper.getInstance().getReadableDatabase();
		if(db != null) return db.rawQuery( query , filterValues );
		return null;
	}
	
	
}
