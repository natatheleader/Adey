package com.redemption.derama.Interface

import com.redemption.derama.Model.Drama
import com.redemption.derama.Model.Episode
import com.redemption.derama.Model.Season
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://drama.allmovielovers.com/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(BASE_URL).build()

interface ApiService{
    @GET("api/drama/published")
    fun getAllData(): Call<List<Drama>>

    @GET("api/season/drama/{id}")
    fun getAllDataSeason(@Path("id") id: String): Call<List<Season>>

    @GET("api/episode/season/{id}")
    fun getAllDataEpisode(@Path("id") id: String): Call<List<Episode>>
}

object Api {
    val retrofitService: ApiService by lazy{retrofit.create(ApiService::class.java)}
}