package com.tsci.paywall

import androidx.lifecycle.viewModelScope
import com.tsci.core.BaseViewModel
import com.tsci.core.util.StringResource
import com.tsci.domain.user.usecase.ISetOnBoardingShownUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaywallViewModel @Inject constructor(
    private val setOnBoardingShownUseCase: ISetOnBoardingShownUseCase
) : BaseViewModel() {


    private val _subscriptionState = MutableStateFlow<SubscriptionState>(SubscriptionState.None)
    val subscriptionState = _subscriptionState.asStateFlow()

    private val _subscriptionSuccess = MutableStateFlow<StringResource>(StringResource.Empty)
    val subscriptionSuccess = _subscriptionSuccess.asStateFlow()

    private val _navigateHome = MutableStateFlow(false)
    val navigateHome = _navigateHome.asStateFlow()

    fun selectMonthlySubscription() {
        viewModelScope.launch {
            _subscriptionState.emit(SubscriptionState.Monthly)
        }
    }

    fun selectYearlySubscription() {
        viewModelScope.launch {
            _subscriptionState.emit(SubscriptionState.Yearly)
        }
    }

    fun selectSubscription() {
        viewModelScope.launch {
            val subscription = subscriptionState.value.type
            _subscriptionSuccess.emit(
                StringResource.ResourceWithResourceArgs(R.string.paywall_subscription_selected, subscription)
            )
        }
    }

    fun closePaywall() {
        request(
            request = {
                setOnBoardingShownUseCase.setShown()
            },
            onSuccess =  {
                _navigateHome.emit(true)
            },
            onError = {
                showError(it)
            }
        )
    }
}