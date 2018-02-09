package com.bitcamp.app.katock;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
        String arr[]=intent.getStringExtra(Intro.TABLE_MEMBER).split(",");

        final ImageView photo=findViewById(R.id.profilephoto);
        final EditText nname=findViewById(R.id.after_name);
        final EditText nphone=findViewById(R.id.after_phone_number);
        final EditText nemail=findViewById(R.id.after_email);
        final EditText naddress = findViewById(R.id.after_address);
        final TextView name=findViewById(R.id.name);
        name.setText(arr[2]+" 프로필 수정");
        nname.setText(arr[2]);
        nemail.setText(arr[3]);
        nphone.setText(arr[4]);
        naddress.setText(arr[6]);
        BitmapFactory.Options options= new BitmapFactory.Options();
        options.inSampleSize = 4;
        Bitmap orgImage = BitmapFactory.decodeResource(getResources(),
                this.getResources()
                        .getIdentifier(
                                this.getPackageName()+":drawable/"
                                        +arr[5],
                                null, null),
                options
        );
        Bitmap resize = orgImage.createScaledBitmap(orgImage, 100, 100, true);
        photo.setImageBitmap(resize);


    }
}
