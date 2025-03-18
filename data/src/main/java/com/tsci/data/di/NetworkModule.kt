package com.tsci.data.di

import com.tsci.data.BuildConfig
import com.tsci.data.NetworkResultCallAdapterFactory
import com.tsci.data.plants.service.PlantsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .let {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(HttpLoggingInterceptor())
                } else {
                    it
                }
            }
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
        .client(okHttpClient)
        .build()


    @Singleton
    @Provides
    fun providePlantsApiService(retrofit: Retrofit): PlantsApiService {
        return retrofit.create(PlantsApiService::class.java)
    }

}