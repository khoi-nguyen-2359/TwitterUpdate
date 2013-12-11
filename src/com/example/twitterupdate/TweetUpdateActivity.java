package com.example.twitterupdate;

import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import com.example.twitterupdate.adapter.TweetUpdateCursorAdapter;
import com.example.twitterupdate.asynctask.UpdateTweetAsyncTask;
import com.example.twitterupdate.contentprovider.DatabaseContract;
import com.example.twitterupdate.loader.TweetUpdateLoader;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.database.ContentObserver;
import android.database.Cursor;

public class TweetUpdateActivity extends Activity implements LoaderCallbacks<Cursor> {

    private ListView lvTweet;
    private TweetUpdateCursorAdapter adapterTweet;
    private TwitterFactory tf;
    
    private ContentObserver mContentObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            Log.d("khoi.na", "onChange db");
            
            onChange(selfChange, null);
        }
        
        @Override
        public void onChange(boolean selfChange, Uri uri) {
            getLoaderManager().restartLoader(0, null, TweetUpdateActivity.this);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTwitterAuth();
        initViews();

        getLoaderManager().restartLoader(0, null, this);
    }

    private void initViews() {
        setContentView(R.layout.activity_tweet_update);
        lvTweet = (ListView) findViewById(R.id.lv_tweet);
        adapterTweet = new TweetUpdateCursorAdapter(this, null);
        lvTweet.setAdapter(adapterTweet);
    }

    private void initTwitterAuth() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey("1yxSOPyNnrGRF4WoRoR5g")
                .setOAuthConsumerSecret("6A0VHO8a5AAFZNDjNX6uoq5zw8eWM7RCeZQM7sKyE0")
                .setOAuthAccessToken("99203743-l1L4JW7e4TV39Ef5SpGr8835ZQkzdBliGZVFCxub3")
                .setOAuthAccessTokenSecret("JSxntAgm87pKlsqbC307DxZazAoWGhLaQ61jhOLZBkIyn");
        tf = new TwitterFactory(cb.build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.action_update: {
            new UpdateTweetAsyncTask(getContentResolver(), tf).execute();
            break;
        }
        default:
            break;
        }
        
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle) {
        return new TweetUpdateLoader(this, DatabaseContract.TWEET_URI, null, null, null, null, mContentObserver);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> paramLoader, Cursor cursor) {
        adapterTweet.swapCursor(cursor);
        Log.d("khoi.na", "onLoadFinished");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> paramLoader) {
        adapterTweet.swapCursor(null);
        Log.d("khoi.na", "onLoaderReset");
    }

}
