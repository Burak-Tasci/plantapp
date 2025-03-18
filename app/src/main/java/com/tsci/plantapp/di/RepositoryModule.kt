package com.tsci.plantapp.di

import com.tsci.data.plants.repository.PlantsRepositoryImpl
import com.tsci.data.user.repository.UserRepositoryImpl
import com.tsci.domain.plants.repository.PlantsRepository
import com.tsci.domain.user.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindPlantsRepository(
        plantsRepositoryImpl: PlantsRepositoryImpl
    ): PlantsRepository
}