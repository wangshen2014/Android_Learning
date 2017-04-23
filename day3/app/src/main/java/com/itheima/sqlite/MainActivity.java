package com.itheima.sqlite;

import android.content.ContentValues;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	private MyOpenHelper myOpenHelper;

	private final String TAG = "DATABASE TEST";
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
		//db.execSQL("insert into info(name,phone) values(?,?)", new Object[]{"zhangsan","1388888"});
		ContentValues contentValues = new ContentValues();
		contentValues.put("name","wangwu");
		contentValues.put("phone","15855514611");
		long result = db.insert("info",null,contentValues);
		Log.i(TAG,"insert " + result + " into table");
		db.close();
		
	}
	
	

	public void click2(View v){
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		//db.execSQL("delete from info where name=?", new Object[]{"zhangsan"});
		int rowAffected = db.delete("info","name=?",new String[]{"wangwu"});
		Log.i(TAG,"delete " + rowAffected + " from table");
		db.close();
		
	}
	

	public void click3(View v){
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		//db.execSQL("update info set phone=? where name=? ", new Object[]{"1488888888888","zhangsan"});
		ContentValues contentValues = new ContentValues();
		contentValues.put("phone","14755773233");
		int rowAffected = db.update("info",contentValues,"name=?",new String[]{"wangwu"});
		Log.i(TAG,"update " + rowAffected + " from table");
		db.close();
	}
	

	public void click4(View v){
		SQLiteDatabase db = myOpenHelper.getReadableDatabase();
//		Cursor cursor = db.rawQuery("select * from info", null);
		String[] name = new String[]{"wangwu"};
		Cursor cursor = db.query("info",new String[]{"phone"},"name=?",name,null,null,null);
		if (cursor!= null&&cursor.getCount()>0) {
			while(cursor.moveToNext()){
//				String name = cursor.getString(1);
				String phone = cursor.getString(0);
				Log.i(TAG,"name:"+name[0]+"----"+phone);
			}
		}
	}

	
}
