package com.redemption.adey

import com.redemption.adey.Model.ItemViewModel
import com.redemption.adey.Network.NetworkLayer

class SharedRepository {
    suspend fun getPlaylist(pageToken : String): ItemViewModel? {
        val request = NetworkLayer.apiClient.getPlaylist(pageToken)
        if(request.failed){
            return  null
        }
        if(!request.isSuccessful){
            return  null
        }
        return request.body
    }
}
