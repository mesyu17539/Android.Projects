package com.bitcamp.app.katock.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitcamp.app.ho.Maps;
import com.bitcamp.app.katock.R;
import com.bitcamp.app.katock.util.Email;
import com.bitcamp.app.katock.util.Phone;

public class MemberDetail extends AppCompatActivity {
    Phone phone;
    Email emeil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memberdetail);

        final Context context=MemberDetail.this;
        final Intent intent=this.getIntent(); //shallow copy
        final String userid=intent.getStringExtra(Intro.MEMBER_1);
        phone =new Phone(context,this);
        emeil=new Email(context,this);
//        String named=intent.getStringExtra(Intro.MEMBER_3);
//        String phonenum=intent.getStringExtra(Intro.MEMBER_5);
        final TextView name=findViewById(R.id.name);
        final EditText phoneNum=findViewById(R.id.phonenum);
        final ImageView profilePhoto=findViewById(R.id.profile_photo);
        final Button detailNum=findViewById(R.id.detail_num);
        final TextView email=findViewById(R.id.email);
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
        final Intro.Member member = (Intro.Member) new Intro.DetailService() {
            @Override
            public Object execute() {
                return item.detail(userid);
            }
        }.execute();
        name.setText(member.name+" 프로필");
        map.setText(member.address);
        phoneNum.setText(member.phoneNumber);
        detailNum.setText(member.phoneNumber);
        email.setText(member.email);


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
//        //전
//        int iPhoto=this.getResources().getIdentifier(this.getPackageName()+":drawable/"+member.profilePhoto,null,null);
//        profilePhoto.setImageDrawable(
//                this.getResources().getDrawable(
//                        iPhoto,
//                        this.getApplicationContext()
//                                .getTheme()));

        findViewById(R.id.call_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone.dial(member.phoneNumber);
            }
        });
        findViewById(R.id.email_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emeil.sendEmail(member.email);
            }
        });
        findViewById(R.id.album_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, Albm.class));
            }
        });
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
        findViewById(R.id.message_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,Massage.class));
            }
        });
        findViewById(R.id.map_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(context, Maps.class);
                member.address="37.5597680, 126.9423080";//split으로 나누어줄거다
                intent1.putExtra("address",member.address);
                startActivity(intent1);
            }
        });{

        }
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
        public Intro.Member detail(String id){
            Intro.Member member=null;
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
                    member = new Intro.Member();
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
