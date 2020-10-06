package com.example.danciben1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {
    final String CREATE_TABLE_SQL="create table tb_danci (_id integer primary key autoincrement, danci, hanyi, shili)";

    public DBOpenHelper(@Nullable MainActivity context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("词典","版本更新————"+oldVersion+"-->"+newVersion);
    }
}
