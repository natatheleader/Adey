package com.redemption.adey

import com.redemption.adey.Interface.YoutubeService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkLayer {

    //test network call
    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    val retrofit = Retrofit.Builder()
        .baseUrl("https://www.googleapis.com/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val youtubeService: YoutubeService by lazy {
        retrofit.create(YoutubeService::class.java)
    }

    val apiClient = ApiClient(youtubeService)
}