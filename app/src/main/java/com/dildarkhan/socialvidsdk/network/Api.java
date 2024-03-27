package com.dildarkhan.socialvidsdk.network;

import com.dildarkhan.socialvidsdk.model.ResponseVideo;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    //String BASE_URL = "https://simplifiedcoding.net/demos/";
    String BASE_URL = "http://fatema.takatakind.com/app_api/";

    /**
     * The return type is important here
     * The class structure that you've defined in Call<T>
     * should exactly match with your json response
     * If you are not using another api, and using the same as mine
     * then no need to worry, but if you have your own API, make sure
     * you change the return type appropriately
     **/
    @GET("index.php?p=showAllVideos")
    Call<JsonObject> getVideos();
}
