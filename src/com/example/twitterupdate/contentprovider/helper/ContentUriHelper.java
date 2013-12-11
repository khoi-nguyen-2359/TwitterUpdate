package com.example.twitterupdate.contentprovider.helper;

import com.example.twitterupdate.contentprovider.DatabaseContract;

import android.content.UriMatcher;

public final class ContentUriHelper {
    public static final int TWEET_CODE = 1;
    
    public static UriMatcher createUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.TWEET_PATH, ContentUriHelper.TWEET_CODE);
        return matcher;
    }
    
    public static String getTableForCode(int match) {
        switch (match) {
        case TWEET_CODE:
            return DatabaseContract.TWEET.TABLE_NAME;
        default:
            break;
        }
        
        return null;
    }
}
