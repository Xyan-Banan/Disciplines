package com.example.disciplines.di

import com.example.disciplines.BuildConfig
import com.example.disciplines.data.source.network.API
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
abstract class ApiModule {
    companion object {
        @Singleton
        @Provides
        fun api(retrofit: Retrofit): API = retrofit.create(API::class.java)

        @Singleton
        @Provides
        fun retrofit(client: OkHttpClient): Retrofit =
            Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        @Singleton
        @Provides
        fun okHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .build()
    }
}