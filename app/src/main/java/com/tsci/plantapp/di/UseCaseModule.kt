package com.tsci.plantapp.di

import com.tsci.domain.user.usecase.GetIsOnBoardingShownUseCase
import com.tsci.domain.user.usecase.IGetIsOnBoardingShownUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {


    @Binds
    @ViewModelScoped
    abstract fun provideGetIsOnBoardingShownUseCase(
        impl: GetIsOnBoardingShownUseCase
    ): IGetIsOnBoardingShownUseCase

}