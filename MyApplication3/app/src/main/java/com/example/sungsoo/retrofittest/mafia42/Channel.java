package com.example.sungsoo.retrofittest.mafia42;

// This is used to map the JSON keys to the object by GSON
public class Channel {

    private  String channel_id;
    private String user_count;


    public String getUser_count() {
        return user_count;
    }

    public void setUser_count(String user_count) {
        this.user_count = user_count;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    @Override
    public String toString() {
        return channel_id+"채널 : "+user_count+"명";
    }
}