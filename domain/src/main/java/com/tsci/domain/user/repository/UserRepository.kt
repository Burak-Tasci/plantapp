package com.tsci.domain.user.repository

import com.tsci.domain.NetworkResult

interface UserRepository {

    fun isOnBoardingShown(): NetworkResult<Boolean>

    fun setOnBoardingShown(): NetworkResult<Unit>
}