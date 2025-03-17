package com.tsci.onboarding.onboarding

import androidx.annotation.DrawableRes

data class OnBoardingItem(
    val title: String,
    val titleSpan: String,
    @DrawableRes val contentDrawable: Int,
    @DrawableRes val brushDrawable: Int
)
