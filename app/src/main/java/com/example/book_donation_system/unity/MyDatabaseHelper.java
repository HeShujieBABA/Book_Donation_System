package com.example.book_donation_system.unity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 何书杰 on 2017/11/11.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String CREATE_RECORD = "create table Record ("
            + "id integer primary key autoincrement,"
            + "score text,"
            + "time text)";
    private Context mContext;
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RECORD);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
