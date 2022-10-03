package com.redemption.derama.Interface

import com.redemption.derama.Model.Drama
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://drama.allmovielovers.com/api/drama/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(BASE_URL).build()

interface ApiService{
    @GET(".")
    fun getAllData(): Call<List<Drama>>
}

object Api {
    val retrofitService: ApiService by lazy{retrofit.create(ApiService::class.java)}
}
