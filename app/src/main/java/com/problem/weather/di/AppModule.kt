package com.problem.weather.di

import com.problem.weather.BuildConfig
import com.problem.weather.network.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesOkHttp(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
            connectTimeout(30L, TimeUnit.SECONDS)
            writeTimeout(30L, TimeUnit.SECONDS)
            readTimeout(30L, TimeUnit.SECONDS)
        }.build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder().apply {
            client(okHttpClient)
            //addConverterFactory(ScalarsConverterFactory.create())
            addConverterFactory(MoshiConverterFactory.create())
        }
    }


    @Provides
    @Singleton
    fun providesWeatherService(retrofit: Retrofit.Builder): WeatherService {
        return retrofit
            .baseUrl("https://api.openweathermap.org/")
            .build()
            .create(WeatherService::class.java)
    }
}