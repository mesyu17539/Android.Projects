package com.bitcamp.app.lotto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView result=findViewById(R.id.result);
        findViewById(R.id.event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.setText(Command.makNum());
            }
        });
    }
}
