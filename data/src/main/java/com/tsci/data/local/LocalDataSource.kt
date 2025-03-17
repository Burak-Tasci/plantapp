package com.tsci.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private const val PREFERENCES_NAME = "preferences"

        private const val KEY_ONBOARDING_SHOWN = "onboarding_shown"
    }

    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)


    fun setOnBoardingShown() {
        preferences.edit().putBoolean(KEY_ONBOARDING_SHOWN, true).apply()
    }

    fun isOnBoardingShown(): Boolean {
        return preferences.getBoolean(KEY_ONBOARDING_SHOWN, false)

    }


}