package com.example.administrator.phonedail;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = (Button) findViewById(R.id.button);
        TextView textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);

//        b.setOnClickListener(new MyClickListener());
        b.setOnClickListener(new View.OnClickListener() {
             @Override
            public void onClick(View v) {
                Editable text = editText.getText();
                String s  = text.toString().trim();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+s));
                startActivity(intent);

                Log.i(TAG,"text.toString()" + s);
            }
        }
        );
    }



//    private class MyClickListener implements View.OnClickListener {
//        @Override
//        public void onClick(View view){
//            Editable text = editText.getText();
//            String s  = text.toString().trim();
//            Intent intent = new Intent();
//            intent.setAction(Intent.ACTION_CALL);
//            intent.setData(Uri.parse("tel:"+s));
//            startActivity(intent);
//
//            Log.i(TAG,"text.toString()" + s);
//        }
//    }
}
