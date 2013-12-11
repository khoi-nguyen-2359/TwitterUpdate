package com.example.twitterupdate;

import java.util.List;

import com.example.twitterupdate.contentprovider.DatabaseContract;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

    private TwitterFactory tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey("1yxSOPyNnrGRF4WoRoR5g")
                .setOAuthConsumerSecret("6A0VHO8a5AAFZNDjNX6uoq5zw8eWM7RCeZQM7sKyE0")
                .setOAuthAccessToken("99203743-l1L4JW7e4TV39Ef5SpGr8835ZQkzdBliGZVFCxub3")
                .setOAuthAccessTokenSecret("JSxntAgm87pKlsqbC307DxZazAoWGhLaQ61jhOLZBkIyn");
        tf = new TwitterFactory(cb.build());

        findViewById(R.id.button1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Thread t = new Thread() {
            @Override
            public void run() {
                // The factory instance is re-useable and thread safe.
                Twitter twitter = tf.getInstance();
                Query query = new Query("singapore");
                query.setCount(10);
                QueryResult result = null;
                List<Status> statusList = null;
//                do {
                    try {
                        result = twitter.search(query);
                    } catch (TwitterException e) {
                        e.printStackTrace();
                    }
                    Log.d("khoi.na", "since id="+result.getSinceId());
                    Log.d("khoi.na", "count="+result.getCount());
                    statusList = result.getTweets();
                    ContentValues[] cvs = new ContentValues[statusList.size()];
                    for (int i = 0; i < statusList.size(); ++i) {
                        long id = statusList.get(i).getId();
                        String content = "@" + statusList.get(i).getUser().getScreenName() + ": " + statusList.get(i).getText();
//                        Log.d("khoi.na", id + " " + content);
                        
                        ContentValues cv = new ContentValues();
                        cv.put(DatabaseContract.TWEET.TWEET_ID, id);
                        cv.put(DatabaseContract.TWEET.CONTENT, content);
                        cv.put(DatabaseContract.TWEET.USER_SCREEN_NAME, statusList.get(i).getUser().getScreenName());
                        cvs[i] = cv;
                    }
                    
                    getContentResolver().bulkInsert(DatabaseContract.TWEET_URI, cvs);
//                } while (statusList != null && statusList.size() != 0);
                Log.d("khoi.na", "log done");
            }
        };
        t.start();
    }
}
