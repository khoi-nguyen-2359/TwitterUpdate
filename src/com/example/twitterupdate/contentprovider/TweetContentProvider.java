package com.example.twitterupdate.contentprovider;

import com.example.twitterupdate.contentprovider.helper.AppSQLiteOpenHelper;
import com.example.twitterupdate.contentprovider.helper.ContentUriHelper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class TweetContentProvider extends ContentProvider {
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
    public int delete(Uri uri, String where, String[] args) {
        SQLiteDatabase db = mSQLiteHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        String table = ContentUriHelper.getTableForCode(match);
        if (table == null)
            return 0;
        
        if (where == null)
            where = "1";
        
        int affRow = db.delete(table, where, args);
        if (affRow > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        Log.d("khoi.na", "del n="+affRow);
        return affRow;
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

        // use CONFLICT_IGNORE specifically in case of this app (just 1 table 'tweet')
        long rowId = db.insertWithOnConflict(table, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        Log.d("khoi.na", "ins id=" + rowId);
        if (rowId < 0)
            return null;

        Uri insertUri = ContentUris.withAppendedId(uri, rowId);
        getContext().getContentResolver().notifyChange(insertUri, null);
        return insertUri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] valuesArray) {
        int count = 0;

        SQLiteDatabase db = mSQLiteHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        String table = ContentUriHelper.getTableForCode(match);

        db.beginTransaction();
        try {
            for (ContentValues values : valuesArray) {
                long rowId = db.insertWithOnConflict(table, null, values, SQLiteDatabase.CONFLICT_IGNORE);
                if (rowId >= 0) {
                    count++;
                }
                
                Log.d("khoi.na", "ins id=" + rowId);
            }

            db.setTransactionSuccessful();
        } catch (SQLException e) {
            count = 0;
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return count;
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
