package com.itheima.sqlite;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

	/**
	 */
	public MyOpenHelper(Context context) {
		super(context, "itheima.db", null,4);
		
		
	}

	/**
	 * Called when the database is created for the first time.
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table info(_id integer primary key autoincrement,name varchar(20),phone varchar(20))");
	}
	/**
	 * Called when the database needs to be upgraded
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("alter table info add phone varchar(20)");
	}

}
