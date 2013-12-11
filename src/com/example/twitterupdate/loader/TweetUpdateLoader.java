package com.example.twitterupdate.loader;

import android.content.Context;
import android.content.CursorLoader;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;

public class TweetUpdateLoader extends CursorLoader {

    private ContentObserver mContentObserver;

    public TweetUpdateLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder, ContentObserver contentObserver) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
        mContentObserver = contentObserver;
    }

    @Override
    public Cursor loadInBackground() {
        Cursor c = getContext().getContentResolver().query(getUri(), null, null, null, null);
        if (c != null && mContentObserver != null) {
            c.registerContentObserver(mContentObserver);
        }

        return c;
    }

}
