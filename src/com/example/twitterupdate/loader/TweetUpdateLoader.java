package com.example.twitterupdate.loader;

import android.content.Context;
import android.content.CursorLoader;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

public class TweetUpdateLoader extends CursorLoader {

    private ContentObserver mContentObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            
            Log.d("khoi.na", "onChange db");
        }
    };

    public TweetUpdateLoader(Context context, Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    public Cursor loadInBackground() {
        Cursor c = getContext().getContentResolver().query(getUri(), null, null, null, null);
        if (c != null) {
            c.registerContentObserver(mContentObserver);
        }
            
        return c;
    }

}
