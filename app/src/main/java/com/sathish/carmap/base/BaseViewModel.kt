package com.sathish.carmap.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    val errorMessage: MutableLiveData<String> = MutableLiveData()

}