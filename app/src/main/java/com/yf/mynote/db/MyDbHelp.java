package com.yf.mynote.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2016/10/8.
 */

public class MyDbHelp extends SQLiteOpenHelper {

    public MyDbHelp(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.i("111","MyDbHelp。。。");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建数据库
        sqLiteDatabase.execSQL("create table  if not exists mynote("
                + "id integer primary key,"
                + "title varchar,"
                + "content varchar,"
                +"time varchar)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //升级数据库

    }
}
