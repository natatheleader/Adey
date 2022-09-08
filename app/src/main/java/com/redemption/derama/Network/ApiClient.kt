package com.redemption.derama.Network

import com.redemption.derama.Interface.YoutubeService
import com.redemption.derama.Model.ItemViewModel
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