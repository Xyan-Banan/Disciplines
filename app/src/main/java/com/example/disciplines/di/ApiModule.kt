package com.example.disciplines.di

import com.example.disciplines.BuildConfig
import com.example.disciplines.data.source.network.API
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
abstract class ApiModule {
    companion object{
        @Singleton
        @Provides
        fun api(retrofit: Retrofit): API = retrofit.create(API::class.java)

        @Singleton
        @Provides
        fun retrofit(): Retrofit =
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}