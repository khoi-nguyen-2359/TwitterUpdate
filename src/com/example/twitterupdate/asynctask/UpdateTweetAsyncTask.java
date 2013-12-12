package com.example.twitterupdate.asynctask;

import java.lang.ref.WeakReference;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.twitterupdate.contentprovider.DatabaseContract;

public class UpdateTweetAsyncTask extends AsyncTask<Void, Void, Integer> {

    private TwitterFactory mTf;
    private ContentResolver mContentResolver;
    private WeakReference<Activity> refActivity;

    public UpdateTweetAsyncTask(Activity activity, ContentResolver contentResolver, TwitterFactory tf) {
        mTf = tf;
        mContentResolver = contentResolver;
        refActivity = new WeakReference<Activity>(activity);
    }

    @Override
    protected Integer doInBackground(Void... params) {
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

        Log.d("khoi.na", result == null ? "null" : "got tweets");
        if (result == null)
            return 0;

        statusList = result.getTweets();
        int nTweet = statusList.size();
        ContentValues[] cvs = new ContentValues[nTweet];
        for (int i = 0; i < nTweet; ++i) {
            twitter4j.Status s = statusList.get(i);
            long id = s.getId();
            String content = s.getText();
            String screenName = null;
            long createdAtSec = s.getCreatedAt().getTime() / 1000;

            if (s.getUser() != null)
                screenName = s.getUser().getScreenName();

            ContentValues cv = new ContentValues();
            cv.put(DatabaseContract.TWEET.TWEET_ID, id);
            cv.put(DatabaseContract.TWEET.CONTENT, content);
            cv.put(DatabaseContract.TWEET.USER_SCREEN_NAME, screenName);
            cv.put(DatabaseContract.TWEET.CREATED_AT, createdAtSec);
            cvs[i] = cv;
        }

        mContentResolver.bulkInsert(DatabaseContract.TWEET_URI, cvs);

        return nTweet;
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);

        Toast.makeText(refActivity.get(), "Got " + result + " more tweets.", Toast.LENGTH_SHORT).show();
    }
}
