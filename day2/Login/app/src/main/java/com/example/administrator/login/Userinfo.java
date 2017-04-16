package com.example.administrator.login;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/16.
 */

public class Userinfo {
    private static String TAG = Userinfo.class.getSimpleName();
    private String mUserName;
    private String mPasswd;
    private boolean isChecked;
    Userinfo(String userName,String passwd){
        mUserName = userName;
        mPasswd = passwd;
    }

    public static boolean saveUserInfo(Context context, String name, String passwd, String isChecked) {
        String result = name + "##" + passwd + "##" + isChecked;
        try {
//            File file = new File("/data/data/com.example.administrator.login/info.txt");
//            FileOutputStream fileOutputStream = new FileOutputStream(file);
            FileOutputStream fileOutputStream = context.openFileOutput("info2.txt",0);
            fileOutputStream.write(result.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {

        }
    }
    public static Map<String , String > getUserinfo(Context context){
        try {
//            File file = new File("/data/data/com.example.administrator.login/info.txt");
//            FileInputStream fileInputStream = new FileInputStream(file);
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            FileInputStream fileInputStream = context.openFileInput("info2.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String result = bufferedReader.readLine();
            if (null == result){
                return null;
            }
            String[] name_passwd = result.split("##");
            String name = name_passwd[0];
            String passwd = name_passwd[1];
            String isChecked = name_passwd[2];
            HashMap hashMap = new HashMap<String,String>();
            Log.i(TAG,"get:" + name + " " + passwd + " " + isChecked);
            hashMap.put("name",name);
            hashMap.put("passwd",passwd);
            hashMap.put("isChecked",isChecked);
            fileInputStream.close();
            return hashMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
