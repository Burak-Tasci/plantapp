package com.tsci.data.user.repository

import com.tsci.data.local.LocalDataSource
import com.tsci.domain.NetworkResult
import com.tsci.domain.user.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
): UserRepository {

    override fun isOnBoardingShown(): NetworkResult<Boolean> {
        val isShown = localDataSource.isOnBoardingShown()
        return NetworkResult.Success(isShown)
    }

    override fun setOnBoardingShown(): NetworkResult<Unit> {
        val setShown = localDataSource.setOnBoardingShown()
        return NetworkResult.Success(setShown)
    }

}