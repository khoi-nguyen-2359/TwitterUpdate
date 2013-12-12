package com.example.twitterupdate.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.twitterupdate.R;
import com.example.twitterupdate.contentprovider.DatabaseContract;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class TweetCursorAdapter extends CursorAdapter {

    public static class ViewHolder {
        TextView tvScreenName;
        TextView tvContent;
        TextView tvCreatedAt;
    }

    public TweetCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View newView = inflater.inflate(R.layout.item_tweet_update, viewGroup, false);

        ViewHolder holder = new ViewHolder();
        holder.tvScreenName = (TextView) newView.findViewById(R.id.tv_screen_name);
        holder.tvContent = (TextView) newView.findViewById(R.id.tv_content);
        holder.tvCreatedAt = (TextView) newView.findViewById(R.id.tv_created_at);

        newView.setTag(holder);

        return newView;
    }
    
    @Override
    protected void onContentChanged() {
        super.onContentChanged();
        Log.d("khoi.na", "onContentChanged");
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        int colIndex = cursor.getColumnIndex(DatabaseContract.TWEET.USER_SCREEN_NAME);
        holder.tvScreenName.setText(cursor.getString(colIndex));

        colIndex = cursor.getColumnIndex(DatabaseContract.TWEET.CONTENT);
        holder.tvContent.setText(cursor.getString(colIndex));

        colIndex = cursor.getColumnIndex(DatabaseContract.TWEET.CREATED_AT);
        long createdAtSec = cursor.getLong(colIndex);
        java.util.Date date = new Date(createdAtSec * 1000);
        DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();
        holder.tvCreatedAt.setText(dateFormat.format(date));
    }

}
