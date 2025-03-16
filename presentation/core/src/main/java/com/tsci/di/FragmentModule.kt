package com.tsci.di

import androidx.fragment.app.Fragment
import com.tsci.core.loading.FragmentLoadingHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped


@Module
@InstallIn(FragmentComponent::class)
class FragmentModule {

    @Provides
    @FragmentScoped
    fun provideLoadingHelper(fragment: Fragment): FragmentLoadingHelper {
        return FragmentLoadingHelper(fragment)
    }
}