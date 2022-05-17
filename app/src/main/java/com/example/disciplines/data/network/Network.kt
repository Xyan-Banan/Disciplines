package com.example.disciplines.data.network

import com.example.disciplines.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Network {
    //alternative to gson
//private val moshi = Moshi.Builder()
//    .add(KotlinJsonAdapterFactory())
//    .build()

    private val retrofit = Retrofit.Builder()
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .build()
        )
//    .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .build()

    val api: Api by lazy { retrofit.create(Api::class.java) }
}