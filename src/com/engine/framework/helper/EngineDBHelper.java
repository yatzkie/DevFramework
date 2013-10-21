/**
 * Reference Author: Napolean A. Patague
 * Author: Ronald Phillip C. Cui
 * Date: Oct 12, 2013
 */
package com.engine.framework.helper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.engine.framework.database.EngineDatabase;
import com.engine.framework.database.Table;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class EngineDBHelper extends SQLiteOpenHelper {

	private EngineDatabase mDatabase;

	/**
	 * @param context - application context
	 * @param name - database name
	 * @param factory - cusorfactory
	 * @param version - database version
	 */
	public EngineDBHelper(Context context, EngineDatabase database) {
		super(context, database.getName(), null, database.getVersion());
		mDatabase = database;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		for(Table table : mDatabase.getTables()) {
			db.execSQL( table.getTableStructure() );
		}
		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		for(Table table : mDatabase.getTables()) {
			db.execSQL( "DROP TABLE IF EXISTS " + table.getName() );
		}
		
		onCreate(db);
		
	}
	
	/**
	 * 
	 * @param sourceDB - InputStream object of the database to be copied
	 * @param outputPath - path to the database of the app
	 * 
	 */
	public boolean copyDatabase(InputStream sourceDB, String outputPath) {
		
		try {
			
	    	InputStream myInput = sourceDB;
	    	String outFileName = outputPath;
	    	OutputStream myOutput = new FileOutputStream( outFileName );
	 
	    	//transfer bytes from the inputfile to the outputfile
	    	byte[] buffer = new byte[1024];
	    	int length;
	    	while ( (length = myInput.read(buffer) ) > 0) {
	    		myOutput.write(buffer, 0, length);
	    	}
	 
	    	//Close the streams
	    	myOutput.flush();
	    	myOutput.close();
	    	myInput.close();
	    	
	    	return true;
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	
	
}
