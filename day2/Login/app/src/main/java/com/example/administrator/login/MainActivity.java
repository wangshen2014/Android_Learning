package com.example.administrator.login;

import android.content.SharedPreferences;
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
        SharedPreferences sharedPreferences = getSharedPreferences("settings",0);
        editTextName.setText(sharedPreferences.getString("name",""));
        editTextPasswd.setText(sharedPreferences.getString("passwd",""));
        checkBox.setChecked(sharedPreferences.getBoolean("isChecked",false));
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
                SharedPreferences sharedPreferences = getSharedPreferences("settings",0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(checkBox.isChecked()){
                        Log.i(TAG,"SAVE:" + name + " " + passwd + " " + isChecked);
                        editor.putString("name",name);
                        editor.putString("passwd",passwd);
                        editor.putBoolean("isChecked",checkBox.isChecked());
                        Toast.makeText(MainActivity.this,"saved",Toast.LENGTH_LONG).show();
                }else{
                        editor.putString("name","");
                        editor.putString("passwd","");
                        editor.putBoolean("isChecked",checkBox.isChecked());
                    }
                editor.commit();
                Toast.makeText(MainActivity.this,"logining",Toast.LENGTH_LONG).show();
            }
        });
    }
}
