package com.tsci.data.di

import com.tsci.data.BuildConfig
import com.tsci.data.NetworkResultCallAdapterFactory
import com.tsci.data.sample.model.SampleResponse
import com.tsci.data.sample.service.SampleApiService
import com.tsci.domain.NetworkError
import com.tsci.domain.NetworkErrorType
import com.tsci.domain.NetworkResult
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
    fun provideSampleApiService(): SampleApiService {
        return object : SampleApiService {
            override suspend fun getSampleData(): NetworkResult<SampleResponse> {
                return NetworkResult.Error(
                    NetworkError(
                        status = 408,
                        type = NetworkErrorType.HTTP,
                        path = null
                    )
                )
            }
        }
    }

}