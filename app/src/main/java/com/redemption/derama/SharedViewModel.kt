package com.redemption.derama

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.redemption.derama.Model.ItemViewModel
import kotlinx.coroutines.launch

class SharedViewModel: ViewModel() {

    private val repository = SharedRepository()

    private val _playlistLiveData = MutableLiveData<ItemViewModel?>()
    val playlistLiveData: LiveData<ItemViewModel?> = _playlistLiveData

    fun refreshPlaylist(pageToken: String) {
        viewModelScope.launch {
            val response = repository.getPlaylist(pageToken)

            _playlistLiveData.postValue(response)
        }
    }
}