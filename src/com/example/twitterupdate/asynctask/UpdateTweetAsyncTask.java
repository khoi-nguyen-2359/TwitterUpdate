package com.example.twitterupdate.asynctask;

import java.util.List;

import com.example.twitterupdate.contentprovider.DatabaseContract;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;

public class UpdateTweetAsyncTask extends AsyncTask<Void, Void, Void> {

    private TwitterFactory mTf;
    private ContentResolver mContentResolver;

    public UpdateTweetAsyncTask(ContentResolver contentResolver, TwitterFactory tf) {
        mTf = tf;
        mContentResolver = contentResolver;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Twitter twitter = mTf.getInstance();
        Query query = new Query("singapore");
        query.setCount(10);
        QueryResult result = null;
        List<twitter4j.Status> statusList = null;
        
        try {
            result = twitter.search(query);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        
        if (result == null)
            return null;

        statusList = result.getTweets();
        ContentValues[] cvs = new ContentValues[statusList.size()];
        for (int i = 0; i < statusList.size(); ++i) {
            twitter4j.Status s = statusList.get(i);
            long id = s.getId();
            String content = s.getText();
            String screenName = null;
            if (s.getUser() != null)
                screenName = s.getUser().getScreenName();

            ContentValues cv = new ContentValues();
            cv.put(DatabaseContract.TWEET.TWEET_ID, id);
            cv.put(DatabaseContract.TWEET.CONTENT, content);
            cv.put(DatabaseContract.TWEET.USER_SCREEN_NAME, screenName);
            cvs[i] = cv;
        }

        mContentResolver.bulkInsert(DatabaseContract.TWEET_URI, cvs);

        return null;
    }

}
