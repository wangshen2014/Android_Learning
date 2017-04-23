package com.itheima.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/4/23.
 */

public class TransactionOpenHelper extends SQLiteOpenHelper {

    public TransactionOpenHelper(Context context){
        super(context,"transaction.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table info(_id integer primary key autoincrement,name varchar(20),balance varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int from, int to){

    }

}
