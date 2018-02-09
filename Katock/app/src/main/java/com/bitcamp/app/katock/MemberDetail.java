package com.bitcamp.app.katock;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitcamp.app.katock.Intro.*;

import java.util.Collection;
import java.util.concurrent.DelayQueue;

public class MemberDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memberdetail);

        final Context context=MemberDetail.this;
        final Intent intent=this.getIntent(); //shallow copy
        final String userid=intent.getStringExtra(Intro.MEMBER_1);
//        String named=intent.getStringExtra(Intro.MEMBER_3);
//        String phonenum=intent.getStringExtra(Intro.MEMBER_5);
        final TextView name=findViewById(R.id.name);
        final EditText phoneNum=findViewById(R.id.phonenum);
        final ImageView profilePhoto=findViewById(R.id.profile_photo);
        TextView map=findViewById(R.id.map);
//        detailName.setText(name+" 프로필");
//        name.setText(named);
//        phoneNum.setText(phonenum);
//        profilePhoto.setImageResource(intent.getStringExtra(Intro.MEMBER_6));

//        MemberItem item=new MemberItem(context);
//        DetailService service=new DetailService() {
//            @Override
//            public Object execute() {
//                return null;
//            }
//        };
//        Member member= (Member) service.execute();

        final MemberItem item=new MemberItem(context);
        final Member member = (Member) new DetailService() {
            @Override
            public Object execute() {
                return item.detail(userid);
            }
        }.execute();
        name.setText(member.name+" 프로필");
        map.setText(member.address);
        phoneNum.setText(member.phoneNumber);


        BitmapFactory.Options options= new BitmapFactory.Options();
        options.inSampleSize = 4;
        Bitmap orgImage = BitmapFactory.decodeResource(getResources(),
                this.getResources()
                        .getIdentifier(
                                this.getPackageName()+":drawable/"
                                        +member.profilePhoto,
                                null, null),
                options
        );
        Bitmap resize = orgImage.createScaledBitmap(orgImage, 100, 100, true);
        profilePhoto.setImageBitmap(resize);
//
//        profilePhoto.setImageDrawable(
//                this.getResources().getDrawable(
//                        this.getResources().getIdentifier(this.getPackageName()+":drawable/"+member.profilePhoto,null,null),
//                        this.getApplicationContext()
//                                .getTheme()));
//        int iPhoto=this.getResources().getIdentifier(this.getPackageName()+":drawable/"+member.profilePhoto,null,null);
//        profilePhoto.setImageDrawable(
//                this.getResources().getDrawable(
//                        iPhoto,
//                        this.getApplicationContext()
//                                .getTheme()));

        findViewById(R.id.name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"짜잔",Toast.LENGTH_LONG);
            }
        });
        findViewById(R.id.update_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,MemberUpdate.class);
                intent.putExtra(Intro.TABLE_MEMBER,member.toString());
                startActivity(intent);
            }
        });
    }
    //추상팩토리 두개가 합쳐 하나로 있다. 인터페이스를 제공하는 패턴.
    private abstract class DetailQuery extends Intro.QueryFactory{//추상팩토리
        SQLiteOpenHelper helper;
        public DetailQuery(Context context) {
            super(context);
            helper=new Intro.SQLiteHelper(context);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }
    private class MemberItem extends DetailQuery{//추상팩토리
        public MemberItem(Context context) {
            super(context);
        }
        public Member detail(String id){
            Member member=null;
            String sql=String.format(
                    "SELECT *" +
                            " FROM %s " +
                            " WHERE %s like '%s'"
                    ,Intro.TABLE_MEMBER
                    ,Intro.MEMBER_1
                    ,id
            );
            Log.d("sql문이닷",sql);
            Cursor cursor =this.getDatabase().rawQuery(sql,null);
            if(cursor!=null){
                    cursor.moveToFirst();
                    member = new Member();
                    member.userid = cursor.getString(cursor.getColumnIndex(Intro.MEMBER_1));
                    member.password = cursor.getString(cursor.getColumnIndex(Intro.MEMBER_2));
                    member.name = cursor.getString(cursor.getColumnIndex(Intro.MEMBER_3));
                    member.email = cursor.getString(cursor.getColumnIndex(Intro.MEMBER_4));
                    member.phoneNumber = cursor.getString(cursor.getColumnIndex(Intro.MEMBER_5));
                    member.profilePhoto = cursor.getString(cursor.getColumnIndex(Intro.MEMBER_6));
                    member.address = cursor.getString(cursor.getColumnIndex(Intro.MEMBER_7));
            }
            Log.d("검색한 회원의 이름", member.userid);
            Log.d("검색한 회원의 이름", member.password);
            return member;
        }
    }
}
