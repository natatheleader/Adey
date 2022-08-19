package com.redemption.adey.Network

import com.redemption.adey.Interface.YoutubeService
import com.redemption.adey.Model.ItemViewModel
import retrofit2.Response


class ApiClient (private val youtubeService: YoutubeService){

    suspend fun getPlaylist(pageToken: String): SimpleResponse<ItemViewModel> {
        return safeApiCall{youtubeService.getPlayList(pageToken)}
    }
    private inline fun <T> safeApiCall(apiCall:()->Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        }catch (e:Exception){
            SimpleResponse.failure(e)
        }
    }

}