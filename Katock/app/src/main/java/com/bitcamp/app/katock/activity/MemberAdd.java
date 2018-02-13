package com.bitcamp.app.katock.activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bitcamp.app.katock.R;

public class MemberAdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memberadd);
        final ImageView profilePhoto=findViewById(R.id.profile_photo);
        final EditText photo=findViewById(R.id.photo);
        final EditText name=findViewById(R.id.name);
        final EditText phoneNumber=findViewById(R.id.phone_number);
        final EditText email=findViewById(R.id.email);
        final EditText address=findViewById(R.id.address);
        final Intro.Member member=new Intro.Member();
        final Context context=MemberAdd.this;

        BitmapFactory.Options options= new BitmapFactory.Options();
        options.inSampleSize = 4;
        Bitmap orgImage = BitmapFactory.decodeResource(getResources(),
                this.getResources()
                        .getIdentifier(
                                this.getPackageName()+":drawable/"
                                        +"profile_0",
                                null, null),
                options
        );
        Bitmap resize = orgImage.createScaledBitmap(orgImage, 600, 600, true);
        profilePhoto.setImageBitmap(resize);

        findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AddMember addMember=new AddMember(context);
                member.name=String.valueOf(name.getText());
                member.phoneNumber=String.valueOf(phoneNumber.getText());
                member.email=String.valueOf(email.getText());
                member.address=String.valueOf(address.getText());
                member.profilePhoto=String.valueOf(photo.getText());
                new Intro.DMLService() {
                        @Override
                        public void execute() {
                            addMember.Add(member);
                        }
                    }.execute();
                startActivity(new Intent(context,MemberList.class));
            }
        });
        findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private abstract class AddQuery extends Intro.QueryFactory {
        Intro.SQLiteHelper helper;
        public AddQuery(Context context) {
            super(context);
            helper=new Intro.SQLiteHelper(context);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getWritableDatabase();
        }
    }
    private class AddMember extends AddQuery {
        public AddMember(Context context) {super(context);}
        public void Add(Intro.Member member){
            String sql=String.format(
                    "INSERT INTO %s ("+
                    " %s, %s, %s, %s, %s, %s)"+
                    " VALUES('%s','%s', '%s', '%s', '%s', '%s');",
                    Intro.TABLE_MEMBER,
                    Intro.MEMBER_2,Intro.MEMBER_3, Intro.MEMBER_4, Intro.MEMBER_5, Intro.MEMBER_6, Intro.MEMBER_7
                    ,1,member.name,member.email,member.phoneNumber,member.profilePhoto,member.address
            );
            Log.d("ADDSQL",sql);
            this.getDatabase().execSQL(sql);
        }
    }
}
