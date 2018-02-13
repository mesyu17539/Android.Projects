package com.bitcamp.app.katock.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.bitcamp.app.katock.R;

import java.util.ArrayList;

public class MemberList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memberlist);

        final Context context=MemberList.this;
        final ListView listView=findViewById(R.id.listView);
        findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,MemberAdd.class));
            }
        });

        //람다형식. 변환순서 오리지널 : 식별자 없이 실행 가능한 함수 표현식
//        final MemberItem item = new MemberItem();
 //        ArrayList<Intro.Member> memberlist=new ArrayList();//크기 모름.
//        Intro.ListService service = new Intro.ListService() {
//            @Override
//            public ArrayList<?> execute() {
//                return null;
//            }
//        };                   //static 이라서 가능
//        memberlist= (ArrayList<Intro.Member>) service.execute();

        //전
//        final MemberItemList itemList=new MemberItemList(context);
//         ArrayList<Intro.Member> memberlist=(ArrayList<Intro.Member>) new Intro.ListService() {
//            @Override
//            public ArrayList<?> execute() {
//                return itemList.list();
//            }
//        }.execute();
//         final MemberItem item = new MemberItem(context,memberlist);
//        listView.setAdapter(item);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
//
//            }
//        });
        //후
        listView.setAdapter(new MemberItem(context,(ArrayList<Intro.Member>) new Intro.ListService() {
            @Override
            public ArrayList<?> execute() {
                return new MemberItemList(context).list();
            }
        }.execute()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Intro.Member selectedMember = (Intro.Member) listView.getItemAtPosition(pos);
                Toast.makeText(context,"인덱스 "+id+"번째 발견! 짜란~",Toast.LENGTH_LONG).show();
                Toast.makeText(context,"빈 객체는 "+selectedMember.userid+"번째 발견! 짜란~",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(context,MemberDetail.class);
                intent.putExtra(Intro.MEMBER_1,selectedMember.userid);//추가
                Log.d("iduser",selectedMember.userid);
                intent.putExtra(Intro.MEMBER_3,selectedMember.name);//추가
                intent.putExtra(Intro.MEMBER_5,selectedMember.phoneNumber);//추가
                intent.putExtra(Intro.MEMBER_6,selectedMember.profilePhoto);//추가
                startActivity(intent);
//                startActivity(new Intent(context,MemberDetail.class).putExtra(Intro.MEMBER_1,selectedMember.userid).putExtra(Intro.MEMBER_3,selectedMember.name).putExtra(Intro.MEMBER_5,selectedMember.phoneNumber));
            }
        });
        findViewById(R.id.logout_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,Index.class));
            }
        });
        //OMG 뭐하려는거냐 스칼라 코딩 이란다
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Toast.makeText(context,"길게 누름",Toast.LENGTH_LONG).show();
                final Intro.Member selectedMember = (Intro.Member) listView.getItemAtPosition(pos);
                new AlertDialog.Builder(context)
                .setTitle("DELETE")
                .setMessage("진짜 삭제하냐잉")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //콜벡영역(실행영역)에는 declar하면 안된다.
                        Toast.makeText(context,"삭제 실행합니다",Toast.LENGTH_LONG).show();
                        new Intro.DMLService() {
                            @Override
                            public void execute() {
                                MemberItemDelete item = new MemberItemDelete(context);
                                item.delete(selectedMember.userid);
                            }
                        }.execute();
                        startActivity(new Intent(context,MemberList.class));
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context,"삭제 취소합니다",Toast.LENGTH_LONG).show();
                    }
                })
                .show();
                return true;
            }
        });
//        //OMG 뭐하려는거냐
//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(context,"길게 누름",Toast.LENGTH_LONG).show();
//                AlertDialog.Builder bs=new AlertDialog.Builder(context);
//                bs.setTitle("DELETE");
//                bs.setMessage("진짜 삭제하냐잉");
//                bs.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(context,"삭제 실행합니다",Toast.LENGTH_LONG).show();
//                    }
//                });
//                bs.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(context,"삭제 취소합니다",Toast.LENGTH_LONG).show();
//                    }
//                });
//                bs.show();
//                return false;
//            }
//        });
    }
    private abstract class DeleteQuery extends Intro.QueryFactory {
        Intro.SQLiteHelper helper;
        public DeleteQuery(Context context) {
            super(context);
            helper=new Intro.SQLiteHelper(context);
        }

        @Override
        public SQLiteDatabase getDatabase() {return helper.getWritableDatabase();}
    }
    private class MemberItemDelete extends DeleteQuery {
        public MemberItemDelete(Context context) {super(context);}
        public void delete(String id) {
            String sql = String.format(
                    "DELETE" +
                    " FROM %S" +
                    " WHERE %s = '%s'"
                    ,Intro.TABLE_MEMBER,
                    Intro.MEMBER_1,
                    id);
            this.getDatabase().execSQL(sql);
            Log.d("DeSQL",sql);
        }
    }
//SQLiteHelper or SQLiteOpenHelper 주의
//멤버필드 : 메모리
//에어리어 cpu
//추상은 객체생성 불가
    private abstract class ListQuery extends Intro.QueryFactory {
        SQLiteOpenHelper helper;
        public ListQuery(Context context) {
            super(context);
            helper=new Intro.SQLiteHelper(context);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }
    private class MemberItemList extends ListQuery{

        public MemberItemList(Context context) {
            super(context);
        }
        public ArrayList<Intro.Member> list(){
            ArrayList<Intro.Member> members=new ArrayList();
            String sql=String.format(
                    "SELECT %s,%s,%s,%s" +
                    " FROM %s"
                    ,Intro.MEMBER_1,Intro.MEMBER_3,Intro.MEMBER_5,Intro.MEMBER_6
                    ,Intro.TABLE_MEMBER);
            Cursor cursor = this.getDatabase()
                    .rawQuery(sql,null);
            Intro.Member member=null;
            //static 이라서 get set 없이한다. 속도 때문에
            if(cursor != null){
                Log.d("접근","중");
                while(cursor.moveToNext()){
                    member =new Intro.Member();
                    member.userid=cursor.getString(cursor.getColumnIndex(Intro.MEMBER_1));
                    member.name=cursor.getString(cursor.getColumnIndex(Intro.MEMBER_3));
                    member.phoneNumber=cursor.getString(cursor.getColumnIndex(Intro.MEMBER_5));
                    member.profilePhoto=cursor.getString(cursor.getColumnIndex(Intro.MEMBER_6));
                    members.add(member);
                }
            }
            Log.d("맴버 수",String.valueOf(members.size()));
            return members;
        }
    }
    //람다 후
    private class MemberItem extends BaseAdapter{//생성자없이 끝나서 추상팩토리아님
        ArrayList<Intro.Member> list;
        LayoutInflater inflater;//대화창 풍선에 글자 띄우기.
        public MemberItem(Context context,ArrayList<Intro.Member> list){
            this.list=list;
            this.inflater=LayoutInflater.from(context);
        }
        private int[] photo={
            R.drawable.profile_1,
                R.drawable.profile_2,
                R.drawable.profile_3,
                R.drawable.profile_4,
                R.drawable.profile_5,
                R.drawable.profile_6,
                R.drawable.lollipop
        };
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View v, ViewGroup g) {
            ViewHoder holder;
            if(v==null){
                v=inflater.inflate(R.layout.member_item,null);
                holder=new ViewHoder();
                holder.profilephoto=v.findViewById(R.id.profile_photo);
                holder.name=v.findViewById(R.id.name);
                holder.phonenum=v.findViewById(R.id.phonenum);
                v.setTag(holder);
            }else{
                holder= (ViewHoder) v.getTag();
            }
            holder.profilephoto.setImageResource(photo[i]);//이거 인덱스로하면 안됨.
//            holder.profilephoto.setImageDrawable(
//                getResources().getDrawable(
//                        getResources().getIdentifier(getPackageName()+":drawable/"+member.profilePhoto,null,null),
//                        getApplicationContext()
//                                .getTheme()));
            holder.name.setText(list.get(i).name);
            holder.phonenum.setText(list.get(i).phoneNumber);
            return v;
        }
    }
    static class ViewHoder{
        ImageView profilephoto;
        TextView name;
        TextView phonenum;
    }


    //람다 전
    /*private class MemberItem extends BaseAdapter{//생성자없이 끝나서 추상팩토리아님

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }
    }*/
}
