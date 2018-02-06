package com.bitcamp.app.katock.mapper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 1027 on 2018-02-06.
 */
/*art enter / insert */
public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "kakao.db";
    public static final String TABLE_NAME = "member";
    public static final String col_1 = "seq";
    public static final String col_2 = "password";
    public static final String col_3 = "name";
    public static final String col_4 = "email";
    public static final String col_5 = "phone_number";
    public static final String col_6 = "profile_photo";
    public static final String col_7 = "address";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "bitcamp.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("Drop table IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
