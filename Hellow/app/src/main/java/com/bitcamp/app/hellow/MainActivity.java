package com.bitcamp.app.hellow;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText inputId=findViewById(R.id.input_id);
        final EditText inputpass=findViewById(R.id.input_pass);
        Button loginBtn=findViewById(R.id.loginbtn);
        Button joinBtn=findViewById(R.id.joinbtn);
        final Context context=MainActivity.this;
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=String.valueOf(inputId.getText());
                String pass=String.valueOf(inputpass.getText());
                Log.d("들어온 ID",id);
                Log.d("들어온 PASS",pass);
                Toast.makeText(context,"들어온 ID "+id+" 들어온 PASS : "+pass,Toast.LENGTH_LONG).show();
            }
        });
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
