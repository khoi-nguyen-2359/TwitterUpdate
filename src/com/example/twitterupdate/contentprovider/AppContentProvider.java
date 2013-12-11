package com.example.twitterupdate.contentprovider;

import com.example.twitterupdate.contentprovider.helper.AppSQLiteOpenHelper;
import com.example.twitterupdate.contentprovider.helper.ContentUriHelper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class AppContentProvider extends ContentProvider {
    private AppSQLiteOpenHelper mSQLiteHelper = null;
    private static UriMatcher sUriMatcher = null;

    static {
        sUriMatcher = ContentUriHelper.createUriMatcher();
    }

    @Override
    public boolean onCreate() {
        mSQLiteHelper = AppSQLiteOpenHelper.getInstance(getContext());
        return true;
    }

    @Override
    public int delete(Uri arg0, String arg1, String[] arg2) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mSQLiteHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        String table = ContentUriHelper.getTableForCode(match);
        if (table == null)
            return null;

        long rowId = db.insert(table, null, values);

        if (rowId < 0)
            return null;

        Uri insertUri = ContentUris.withAppendedId(uri, rowId);
        getContext().getContentResolver().notifyChange(insertUri, null);
        Log.d("khoi.na", "ins id=" + rowId);
        return insertUri;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int match = sUriMatcher.match(uri);

        SQLiteDatabase db = mSQLiteHelper.getReadableDatabase();

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(ContentUriHelper.getTableForCode(match));
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

}
