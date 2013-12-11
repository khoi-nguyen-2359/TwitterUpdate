package com.example.twitterupdate.contentprovider.helper;

import java.io.IOException;
import java.io.InputStream;

import com.example.twitterupdate.R;
import com.example.twitterupdate.contentprovider.DatabaseContract;
import com.example.twitterupdate.util.TextUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AppSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "tweet_update.db";
    private static final int DB_VERSION = 5;
    private static String DB_CREATE_SQL = "CREATE TABLE tweet (" + "tweet_id INTEGER PRIMARY KEY NOT NULL,"
            + "user_screen_name VARCHAR," + "content TEXT" + ");";

    private static AppSQLiteOpenHelper mInstance = null;

    public static AppSQLiteOpenHelper getInstance(Context context) {
        if (mInstance == null)
            mInstance = new AppSQLiteOpenHelper(context);

        return mInstance;
    }

    public AppSQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        loadDbCreationSQL(context);
    }

    private void loadDbCreationSQL(Context context) {
        InputStream is = context.getResources().openRawResource(R.raw.db_create);
        DB_CREATE_SQL = TextUtil.toString(is);
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // do upgrade stuff
        Log.d("khoi.na", "onUpgrade,oldVer=" + oldVersion + ",newVer=" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TWEET.TABLE_NAME);
        onCreate(db);
    }
}
