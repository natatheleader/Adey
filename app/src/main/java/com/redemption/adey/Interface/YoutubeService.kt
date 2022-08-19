package com.redemption.adey.Interface

import com.redemption.adey.Model.ItemViewModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeService {

    @GET("youtube/v3/playlistItems?part=snippet&maxResults=10&playlistId=PLZNOB26AhBefNZYpLMV-zVuiI8lsovZUb&key=AIzaSyDE2wMvuGg7-18C5vnDoEJEPtLgaNLuwhU")
    suspend fun getPlayList(@Query("pageToken") pageToken:String): Response<ItemViewModel>
}