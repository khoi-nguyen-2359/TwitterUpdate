package com.example.twitterupdate;

import com.example.twitterupdate.adapter.TweetUpdateCursorAdapter;
import com.example.twitterupdate.contentprovider.DatabaseContract;
import com.example.twitterupdate.loader.TweetUpdateLoader;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.database.Cursor;

public class TweetUpdateActivity extends Activity implements LoaderCallbacks<Cursor> {

    private ListView lvTweet;
    private TweetUpdateCursorAdapter adapterTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_update);

        lvTweet = (ListView) findViewById(R.id.lv_tweet);
        adapterTweet = new TweetUpdateCursorAdapter(this, null);
        lvTweet.setAdapter(adapterTweet);
        
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle) {
        return new TweetUpdateLoader(this, DatabaseContract.TWEET_URI, null, null, null, null);
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
