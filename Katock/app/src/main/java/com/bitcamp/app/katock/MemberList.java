package com.bitcamp.app.katock;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.view.LayoutInflaterFactory;
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

import java.util.ArrayList;

public class MemberList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memberlist);

        final Context context=MemberList.this;
        final ListView listView=findViewById(R.id.listView);

        //람다형식. 변환순서 오리지널 : 식별자 없이 실행 가능한 함수 표현식
//        final MemberItem item = new MemberItem();
 //        ArrayList<Intro.Member> memberlist=new ArrayList();//크기 모름.
//        Intro.ListService service = new Intro.ListService() {
//            @Override
//            public ArrayList<?> execute() {
//                return null;
//            }
//        };
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

            }
        });


    }
    private abstract class ListQuery extends Intro.QueryFactory{
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
            Log.d("접근","쿼리"+String.format(
                    "SELECT %s,%s,%s,%s" +
                            " FROM %s"
                    ,Intro.MEMBER_1,Intro.MEMBER_3,Intro.MEMBER_5,Intro.MEMBER_6
                    ,Intro.TABLE_MEMBER));
            Cursor cursor = this.getDatabase()
                    .rawQuery(sql,null);
            Intro.Member member=null;
            //static 이라서 get set 없이한다. 속도 때문에
            if(cursor != null){
                Log.d("접근","중");
                while(cursor.moveToNext()){
                    member =new Intro.Member();
                    member.userid=cursor.getString(cursor.getColumnIndex(Intro.MEMBER_1));
                    Log.d("접근","맴버"+ member.userid);
                    member.name=cursor.getString(cursor.getColumnIndex(Intro.MEMBER_3));
                    Log.d("접근","맴버"+member.name);
                    member.phoneNumber=cursor.getString(cursor.getColumnIndex(Intro.MEMBER_5));
                    Log.d("접근","맴버"+member.phoneNumber);
                    member.profilePhoto=cursor.getString(cursor.getColumnIndex(Intro.MEMBER_6));
                    Log.d("접근","맴버"+member.profilePhoto);
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
            R.drawable.cupcake,
                R.drawable.eclair,
                R.drawable.froyo,
                R.drawable.donut,
                R.drawable.icecream,
                R.drawable.lollipop
                ,R.drawable.gingerbread
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
            holder.profilephoto.setImageResource(photo[i]);
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
