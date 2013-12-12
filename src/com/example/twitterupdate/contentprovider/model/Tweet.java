package com.example.twitterupdate.contentprovider.model;

public final class Tweet {
    private long id;
    private long tweetId;
    private String content;
    private String userScreenName;
    private long createdAtSec;

    public long getId() {
        return id;
    }

    public void setId(long mId) {
        this.id = mId;
    }

    public long getTweetId() {
        return tweetId;
    }

    public void setTweetId(long mTweetId) {
        this.tweetId = mTweetId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String mContent) {
        this.content = mContent;
    }

    public String getUserScreenName() {
        return userScreenName;
    }

    public void setUserScreenName(String userScreenName) {
        this.userScreenName = userScreenName;
    }

    public long getCreatedAtSec() {
        return createdAtSec;
    }

    public void setCreatedAtSec(long createdAtSec) {
        this.createdAtSec = createdAtSec;
    }
}
