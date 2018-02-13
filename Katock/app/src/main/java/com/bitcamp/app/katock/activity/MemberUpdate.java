package com.bitcamp.app.katock.activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitcamp.app.katock.R;

public class MemberUpdate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memberupdate);

        final Context context=MemberUpdate.this;
        final ImageView photo=findViewById(R.id.profilephoto);
        final EditText nname=findViewById(R.id.after_name);
        final EditText nphone=findViewById(R.id.after_phone_number);
        final EditText nemail=findViewById(R.id.after_email);
        final EditText naddress = findViewById(R.id.after_address);
        final EditText afterPhoto=findViewById(R.id.after_photo);
        final TextView name=findViewById(R.id.name);
        final Intro.Member member=new Intro.Member();
        final String[] arr=getIntent().getStringExtra(Intro.TABLE_MEMBER).split(",");
        name.setHint(arr[2]+" 프로필 수정");
        nname.setHint(arr[2]);
        nemail.setHint(arr[3]);
        nphone.setHint(arr[4]);
        naddress.setHint(arr[6]);
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
        findViewById(R.id.confirm_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("이름 ",nname.getText().toString());
//                if(nname.getText().toString().equals("")){
//                    member.name=arr[2];
//                }else{
//                    member.name=nname.getText().toString();
//                }
                member.userid=arr[0];
                Log.d("UPID ",arr[0]);

                member.name=(
                        (nname.getText().toString().equals("")))?
                                arr[2]:
                                nname.getText().toString();
                member.profilePhoto=
                        (afterPhoto.getText().toString().equals(""))?
                                arr[5]:
                                afterPhoto.getText().toString();
                member.email=(
                        (nemail.getText().toString()).equals(""))?
                                arr[3]:
                                nemail.getText().toString();
                member.phoneNumber=
                        (nphone.getText().toString().equals(""))?
                                arr[4]:
                                nphone.getText().toString();
                member.address=
                        (naddress.getText().toString().equals(""))?
                                arr[6]:
                                naddress.getText().toString();

//                Intro.UpdateService service= new Intro.UpdateService(){
//
//                    @Override
//                    public void execute(Object o) {
//
//                    }
//                };
                new Intro.DMLService(){
                    @Override
                    public void execute() {
                        UpdateMember item =new UpdateMember(context);
                        item.memupdate(member);
                    }
                }.execute();
                Intent intent = new Intent(context,MemberDetail.class);
                intent.putExtra(Intro.MEMBER_1,arr[0]);
                startActivity(intent);
            }
        });
        findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context=MemberUpdate.this;
                Intent intent = new Intent(context,MemberDetail.class);
                intent.putExtra(Intro.MEMBER_1,arr[0]);
                startActivity(intent);
            }
        });
    }
    //추상팩토리 두개가 합쳐 하나로 있다. 인터페이스를 제공하는 패턴.
    //Open 은 전역 DB이므로 가입시 한번만 써야한다. 그다음부터 모든 기능은 SQLHelper를 써야함.
    private abstract class UpdateQuery extends Intro.QueryFactory{//추상팩토리
        SQLiteOpenHelper helper;
        public UpdateQuery(Context context) {
            super(context);
            helper=new Intro.SQLiteHelper(context);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getWritableDatabase();
        }
    }
    private class UpdateMember extends UpdateQuery{

        public UpdateMember(Context context) {
            super(context);
        }
        public void memupdate(Intro.Member member){
            String sql=String.format(
                    "UPDATE %s SET" +
                    " %s = '%s'" +
                            ", %s = '%s'" +
                            ", %s = '%s'" +
                            ", %s = '%s'" +
                            ", %s = '%s'" +
                    " WHERE %s LIKE '%s'",
                    Intro.TABLE_MEMBER,
                    Intro.MEMBER_6, member.profilePhoto,
                    Intro.MEMBER_5,member.phoneNumber,
                    Intro.MEMBER_3,member.name,
                    Intro.MEMBER_4,member.email,
                    Intro.MEMBER_7,member.address,
                    Intro.MEMBER_1,member.userid
            );
            this.getDatabase().execSQL(sql);
            Log.d("UpSQL",sql);
        }
    }
}
