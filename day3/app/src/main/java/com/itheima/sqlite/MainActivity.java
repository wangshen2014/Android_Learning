package com.itheima.sqlite;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	private MyOpenHelper myOpenHelper;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		myOpenHelper = new MyOpenHelper(getApplicationContext());
//		SQLiteDatabase sqLiteDatabase = myOpenHelper.getWritableDatabase();
//		SQLiteDatabase readableDatabase = myOpenHelper.getReadableDatabase();
		
		
	}
	

	public void click1(View v){
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		db.execSQL("insert into info(name,phone) values(?,?)", new Object[]{"zhangsan","1388888"});
		db.close();
		
	}
	
	

	public void click2(View v){
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		db.execSQL("delete from info where name=?", new Object[]{"zhangsan"});
		db.close();
		
	}
	

	public void click3(View v){
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		db.execSQL("update info set phone=? where name=? ", new Object[]{"1488888888888","zhangsan"});
		db.close();
	}
	

	public void click4(View v){
		SQLiteDatabase db = myOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from info", null);
		if (cursor!= null&&cursor.getCount()>0) {
			while(cursor.moveToNext()){
				String name = cursor.getString(1);
				String phone = cursor.getString(2);
				Log.i("SQLTEST","name:"+name+"----"+phone);
			}
		}
	}

	
}
