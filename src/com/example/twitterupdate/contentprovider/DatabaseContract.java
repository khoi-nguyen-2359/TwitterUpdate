package com.example.twitterupdate.contentprovider;

import android.net.Uri;

public final class DatabaseContract {
    public static final class TWEET {
        public static final String TABLE_NAME = "tweet";
        public static final String TWEET_ID = "tweet_id";
        public static final String CONTENT = "content";
    }

    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "com.example.twitterupdate.provider";

    public static final String TWEET_PATH = TWEET.TABLE_NAME;
    public static final Uri TWEET_URI = Uri.parse(DatabaseContract.SCHEME + DatabaseContract.AUTHORITY + "/"
            + TWEET_PATH);

}
