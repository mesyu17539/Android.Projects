package com.bitcamp.app.katock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MemberUpdate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memberupdate);

        final Intent intent=this.getIntent();
        String arr[]=intent.toString().split(",");

        final ImageView photo=findViewById(R.id.profilephoto);
        final EditText nname=findViewById(R.id.after_name);
        final EditText nphone=findViewById(R.id.after_phone_number);
        final EditText nemail=findViewById(R.id.after_email);
        final EditText naddress = findViewById(R.id.after_address);
        final TextView name=findViewById(R.id.name);
        name.setText(arr[2]+" 프로필 수정");
        nname.setText(arr[2]);
        naddress.setText(arr[6]);



    }
}
