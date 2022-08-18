package com.redemption.adey

import com.google.gson.GsonBuilder
import com.redemption.adey.Interface.YoutubeService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkLayer {

    //test network call
    val gson = GsonBuilder()
        .create()
    val retrofit = Retrofit.Builder()
        .baseUrl("https://www.googleapis.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val youtubeService: YoutubeService by lazy {
        retrofit.create(YoutubeService::class.java)
    }

    val apiClient = ApiClient(youtubeService)
}