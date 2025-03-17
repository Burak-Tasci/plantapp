package com.tsci.plantapp

import com.tsci.core.BaseViewModel
import com.tsci.domain.user.usecase.IGetIsOnBoardingShownUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getIsOnBoardingShownUseCase: IGetIsOnBoardingShownUseCase
): BaseViewModel() {

    companion object {
        const val INVALID_START_DESTINATION = 0
    }

    private val _startDestination = MutableStateFlow(INVALID_START_DESTINATION)
    val startDestination: StateFlow<Int> = _startDestination.asStateFlow()

    private val _finishActivity = MutableStateFlow(false)
    val finishActivity = _finishActivity.asStateFlow()

    init {
        checkStartDestination()
    }

    private fun checkStartDestination() {
        request(
            request = {
                getIsOnBoardingShownUseCase.isOnBoardingShown()
            },
            onSuccess = { isShown ->
                val startDestinationId = if (isShown) {
                    // todo should be home
                    INVALID_START_DESTINATION
                } else {
                    com.tsci.onboarding.R.id.nav_graph_onboarding
                }
                _startDestination.emit(startDestinationId)
            },
            onError = {
                _finishActivity.emit(true)
            }
        )
    }

}