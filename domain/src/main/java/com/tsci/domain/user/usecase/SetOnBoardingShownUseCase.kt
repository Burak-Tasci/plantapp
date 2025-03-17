package com.tsci.domain.user.usecase

import com.tsci.domain.NetworkResult
import com.tsci.domain.user.repository.UserRepository
import javax.inject.Inject

class SetOnBoardingShownUseCase @Inject constructor(
    private val userRepository: UserRepository
) : ISetOnBoardingShownUseCase {

    override fun setShown(): NetworkResult<Unit> {
        return userRepository.setOnBoardingShown()
    }
}


interface ISetOnBoardingShownUseCase {
    fun setShown(): NetworkResult<Unit>
}