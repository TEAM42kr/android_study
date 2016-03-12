package com.example.sungsoo.retrofittest.mafia42;

import com.example.sungsoo.retrofittest.stackoverflow.StackOverflowQuestions;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface Mafia42API {
    @GET("/channel.php")
    Call<List<Channel>> loadChannelList();
} 