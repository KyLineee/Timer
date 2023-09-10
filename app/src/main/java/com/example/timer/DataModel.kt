package com.example.timer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataModel : ViewModel() {
    val massage: MutableLiveData<Long> by lazy {
        MutableLiveData<Long>()
    }
}