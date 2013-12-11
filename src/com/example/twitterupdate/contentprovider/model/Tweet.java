package com.example.twitterupdate.contentprovider.model;

public final class Tweet {
    private long mId;
    private long mTweetId;
    private String mContent;

    public long getId() {
        return mId;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

    public long getTweetId() {
        return mTweetId;
    }

    public void setTweetId(long mTweetId) {
        this.mTweetId = mTweetId;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }
}
