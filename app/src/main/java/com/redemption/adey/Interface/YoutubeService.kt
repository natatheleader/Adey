package com.redemption.adey.Interface

import com.redemption.adey.Model.ItemViewModel
import retrofit2.Call
import retrofit2.http.GET

interface YoutubeService {

    @GET("youtube/v3/playlistItems?part=snippet&playlistId=PLZNOB26AhBefNZYpLMV-zVuiI8lsovZUb&key=AIzaSyDE2wMvuGg7-18C5vnDoEJEPtLgaNLuwhU")
    fun getPlayList(): Call<ItemViewModel>
}