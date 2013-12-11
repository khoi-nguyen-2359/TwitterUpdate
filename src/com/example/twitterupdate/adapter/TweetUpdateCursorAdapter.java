package com.example.twitterupdate.adapter;

import com.example.twitterupdate.R;
import com.example.twitterupdate.contentprovider.DatabaseContract;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class TweetUpdateCursorAdapter extends CursorAdapter {

    public static class ViewHolder {
        TextView screenName;
        TextView content;
    }

    public TweetUpdateCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View newView = inflater.inflate(R.layout.item_tweet_update, viewGroup, false);

        ViewHolder holder = new ViewHolder();
        holder.screenName = (TextView) newView.findViewById(R.id.tv_screen_name);
        holder.content = (TextView) newView.findViewById(R.id.tv_content);
        
        newView.setTag(holder);
        
        return newView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        
        int colIndex = cursor.getColumnIndex(DatabaseContract.TWEET.USER_SCREEN_NAME);
        holder.screenName.setText(cursor.getString(colIndex));
        colIndex = cursor.getColumnIndex(DatabaseContract.TWEET.CONTENT);
        holder.content.setText(cursor.getString(colIndex));
    }

}
