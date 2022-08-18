package com.redemption.adey

import android.util.Log
import com.redemption.adey.Interface.YoutubeService
import com.redemption.adey.Model.ItemViewModel
import retrofit2.Response


class ApiClient (private val youtubeService: YoutubeService){

    suspend fun getPlaylist(): SimpleResponse<ItemViewModel> {
        return safeApiCall{youtubeService.getPlayList()}
    }
    private inline fun <T> safeApiCall(apiCall:()->Response<T>):SimpleResponse<T>{
        return try {
            SimpleResponse.success(apiCall.invoke())
        }catch (e:Exception){
            Log.d("ApiError", "Error: $e")
            SimpleResponse.failure(e)
        }
    }

}