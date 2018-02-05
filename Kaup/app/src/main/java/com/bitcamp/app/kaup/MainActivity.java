package com.bitcamp.app.kaup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText name=findViewById(R.id.name);
        final EditText height=findViewById(R.id.height);
        final EditText weight=findViewById(R.id.weight);
        final TextView textView=findViewById(R.id.result);

        findViewById(R.id.event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                textView.setText(Command.kaup(Command.changeDouble(weight),Command.changeDouble(height),String.valueOf(name.getText())));
            }
        });
    }
}
