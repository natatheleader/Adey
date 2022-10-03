package com.redemption.derama.Interface

import com.redemption.derama.Model.Season
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://drama.allmovielovers.com/api/season/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(BASE_URL).build()

interface ApiServiceSeason {
    @GET(".")
    fun getAllSeasonData(): Call<List<Season>>
}

object ApiSeason {
    val retrofitService: ApiServiceSeason by lazy{retrofit.create(ApiServiceSeason::class.java)}
}