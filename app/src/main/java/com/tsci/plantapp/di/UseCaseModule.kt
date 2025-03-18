package com.tsci.plantapp.di

import com.tsci.domain.plants.usecase.GetCategoriesUseCase
import com.tsci.domain.plants.usecase.GetQuestionsUseCase
import com.tsci.domain.plants.usecase.IGetCategoriesUseCase
import com.tsci.domain.plants.usecase.IGetQuestionsUseCase
import com.tsci.domain.user.usecase.GetIsOnBoardingShownUseCase
import com.tsci.domain.user.usecase.IGetIsOnBoardingShownUseCase
import com.tsci.domain.user.usecase.ISetOnBoardingShownUseCase
import com.tsci.domain.user.usecase.SetOnBoardingShownUseCase
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

    @Binds
    @ViewModelScoped
    abstract fun provideSetOnBoardingShownUseCase(
        impl: SetOnBoardingShownUseCase
    ): ISetOnBoardingShownUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideGetQuestionsUseCase(
        impl: GetQuestionsUseCase
    ): IGetQuestionsUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideGetCategoriesUseCase(
        impl: GetCategoriesUseCase
    ): IGetCategoriesUseCase

}