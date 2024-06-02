package com.example.examplemod.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface ApiInterface {

    @Headers({
        "Accept: application/json",
        "Content-type: application/json"
    })
    @POST("/api/network/{type}")
    Call<ResponseBody> postNetworkPing(@Path("type") String type , List<NetworkData> networkData);
}
