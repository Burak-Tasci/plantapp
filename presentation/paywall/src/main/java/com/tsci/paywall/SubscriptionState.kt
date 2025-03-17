package com.tsci.paywall

import androidx.annotation.StringRes

sealed interface SubscriptionState {

    @get:StringRes
    val type: Int

    data object None: SubscriptionState {
        override val type: Int = R.string.paywall_subscription_none
    }

    data object Monthly: SubscriptionState {
        override val type: Int = R.string.paywall_subscription_monthly
    }

    data object Yearly: SubscriptionState {
        override val type: Int = R.string.paywall_subscription_yearly
    }
}