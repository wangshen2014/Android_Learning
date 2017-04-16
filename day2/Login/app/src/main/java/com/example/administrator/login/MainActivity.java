package com.example.administrator.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    private EditText editTextName;
    private EditText editTextPasswd;
    private CheckBox checkBox;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = (EditText) findViewById(R.id.et_username);
        editTextPasswd = (EditText) findViewById(R.id.et_passwd);
        checkBox = (CheckBox) findViewById(R.id.checkbox_rem);
        Map<String,String> hashMap = Userinfo.getUserinfo(MainActivity.this);
        if(hashMap != null){
            editTextName.setText(hashMap.get("name"));
            editTextPasswd.setText(hashMap.get("passwd"));
            Log.i(TAG,hashMap.get("isChecked"));
            if ("Remember".equals(hashMap.get("isChecked"))){
                checkBox.setChecked(true);
            }
        }
        button = (Button) findViewById(R.id.button_login);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String name = editTextName.getText().toString().trim();
                String passwd = editTextPasswd.getText().toString().trim();
                String isChecked = checkBox.getText().toString().trim();
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(passwd)){
                    Toast.makeText(MainActivity.this,"UserName or Passwd can not be empty",Toast.LENGTH_LONG).show();
                }

                if(checkBox.isChecked()){
                    Log.i(TAG,"SAVE:" + name + " " + passwd + " " + isChecked);
                    Userinfo.saveUserInfo(MainActivity.this,name,passwd,isChecked);
                    Toast.makeText(MainActivity.this,"saved",Toast.LENGTH_LONG).show();
                }else{
                    File file = new File(getApplicationContext().getFilesDir(),"info2.txt");
                        if(file.exists()){
                            file.delete();
                            Toast.makeText(MainActivity.this,"deleting",Toast.LENGTH_SHORT).show();
                        }
                    }
                Toast.makeText(MainActivity.this,"logining",Toast.LENGTH_LONG).show();
            }
        });
    }
}
