package com.example.twitterupdate;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        Thread t = new Thread() {
            @Override
            public void run() {
                // The factory instance is re-useable and thread safe.
                Twitter twitter = tf.getInstance();
                Query query = new Query("Singapore");
                QueryResult result = null;
                try {
                    result = twitter.search(query);
                } catch (TwitterException e) {
                    e.printStackTrace();
                }

                for (Status status : result.getTweets()) {
                    Log.d("khoi.na", "@" + status.getUser().getScreenName() + ":" + status.getText());
                }
                
                Log.d("khoi.na", "log done");
            }
        };
        t.start();
    }
}
