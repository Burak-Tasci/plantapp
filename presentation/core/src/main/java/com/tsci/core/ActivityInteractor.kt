package com.tsci.core

import androidx.lifecycle.LiveData

interface ActivityInteractor {

    val navigationBarHeightLiveData: LiveData<Int>

    val statusBarHeightLiveData: LiveData<Int>
}