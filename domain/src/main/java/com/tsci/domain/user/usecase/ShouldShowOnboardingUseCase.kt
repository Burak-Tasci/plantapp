package com.tsci.domain.user.usecase

import com.tsci.domain.NetworkResult
import com.tsci.domain.user.repository.UserRepository
import javax.inject.Inject

class GetIsOnBoardingShownUseCase @Inject constructor(
    private val userRepository: UserRepository
) : IGetIsOnBoardingShownUseCase {

    override fun isOnBoardingShown(): NetworkResult<Boolean> {
        return userRepository.isOnBoardingShown()
    }
}


interface IGetIsOnBoardingShownUseCase {
    fun isOnBoardingShown(): NetworkResult<Boolean>
}