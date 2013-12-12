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

public class ClearTweetAsyncTask extends AsyncTask<Void, Void, Integer> {

    private TwitterFactory mTf;
    private ContentResolver mContentResolver;
    private WeakReference<Activity> refActivity;

    public ClearTweetAsyncTask(Activity activity, ContentResolver contentResolver, TwitterFactory tf) {
        mTf = tf;
        mContentResolver = contentResolver;
        refActivity = new WeakReference<Activity>(activity);
    }

    @Override
    protected Integer doInBackground(Void... params) {
        return mContentResolver.delete(DatabaseContract.TWEET_URI, null, null);
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);

        if (refActivity.get() != null)
            Toast.makeText(refActivity.get(), "Cleared " + result + " tweets.", Toast.LENGTH_SHORT).show();
    }
}
