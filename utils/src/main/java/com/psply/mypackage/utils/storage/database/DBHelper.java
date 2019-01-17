package com.psply.mypackage.utils.storage.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Data: 2018/12/24
 * Author: shipan
 * Description:
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,dbName,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
