package com.tsci.paywall

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class PaywallFeatureItem(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    @StringRes val subtitle: Int,
)