package com.example.itunesmichael.api

import com.example.itunesmichael.model.SongsListResponse
import retrofit2.Call
import retrofit2.http.GET

public interface ApiInterface {
    @GET("search?term=Michael+jackson")
     fun getSongList(): Call<SongsListResponse>
}