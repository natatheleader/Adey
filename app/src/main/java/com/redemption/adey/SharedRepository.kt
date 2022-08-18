package com.redemption.adey

import android.util.Log
import com.redemption.adey.Model.ItemViewModel

class SharedRepository {
    suspend fun getPlaylist(): ItemViewModel? {
        val request = NetworkLayer.apiClient.getPlaylist()
        if(request.failed){
            Log.d("ApiError","Error::: "+ request.exception)
            return  null
        }
        if(!request.isSuccessful){
            Log.d("ApiError","Error::: "+ request.data)
            return  null
        }
//        Log.d("ApiSucess","Model Error:  "+request.body)
            return request.body

    }
}
