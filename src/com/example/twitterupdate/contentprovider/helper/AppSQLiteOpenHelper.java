package com.example.twitterupdate.contentprovider.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "tweet_update.db";
    private static final int DB_VERSION = 1;
    private static final String DB_CREATE_SQL = "CREATE TABLE tweet (" + "tweet_id INTEGER PRIMARY KEY NOT NULL,"
            + "content TEXT NOT NULL" + ");";
    
    private static AppSQLiteOpenHelper mInstance = null;

    public static AppSQLiteOpenHelper getInstance(Context context) {
        if (mInstance == null)
            mInstance = new AppSQLiteOpenHelper(context);

        return mInstance;
    }

    public AppSQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // do upgrade stuff
    }
}
