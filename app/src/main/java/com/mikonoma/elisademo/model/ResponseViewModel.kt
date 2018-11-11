package com.mikonoma.elisademo.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mikonoma.elisademo.network.ENWResponse

class ResponseViewModel : ViewModel() {

    val response: MutableLiveData<ENWResponse> by lazy {
        MutableLiveData<ENWResponse>()
    }


}