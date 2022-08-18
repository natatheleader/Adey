package com.redemption.adey

import com.redemption.adey.Interface.YoutubeService
import com.redemption.adey.Model.ItemViewModel
import retrofit2.Response

class ApiClient (
    private val youtubeService: YoutubeService
) {
    suspend fun getPlaylist(): Response<ItemViewModel> {
        return youtubeService.getPlayList()
    }
}