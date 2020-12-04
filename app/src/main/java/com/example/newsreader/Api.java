package com.example.newsreader;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    String BASE_URL = "http://newsapi.org/v2/";


    @GET("top-headlines?country=us&category=business&apiKey=ae52924eaf8a4a96b48e182319523e55")
    Call<News> getNews();
}
