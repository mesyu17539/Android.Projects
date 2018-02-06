package com.bitcamp.app.katock;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);

        final EditText phonenum=findViewById(R.id.phonenum);
        final Context context=Intro.this;

//      람다 자바.
        findViewById(R.id.introbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"인증 받을 전화번호 : "
                        + phonenum.getText(),Toast.LENGTH_LONG).show();
                Intent intent=new Intent(context,Index.class);
//                                    from 여기 A to 목적지 B
                startActivity(intent);
            }
        });
    }
}
