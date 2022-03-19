package com.example.disciplines.data.network

import com.example.disciplines.data.network.model.DisciplinesPair
import com.example.disciplines.data.network.model.Elective
import com.example.disciplines.data.network.model.MobilityModule
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//private const val BASE_URL = "https://disciplines.getsandbox.com"
private const val BASE_URL = "https://dogsbetterthancats.blankhex.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .client(
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()
    )
    .addConverterFactory(MoshiConverterFactory.create(moshi))
//    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface Api {
    @GET("disciplinesByChoice")
    suspend fun getDisciplinesByChoice(@Query("groupName") name: String): Response<DisciplinesPair>
//            List<DisciplinesPair>

    @GET("mobilityModules/{groupName}")
    suspend fun getMobilityModules(@Path("groupName") name: String): List<MobilityModule>

    @GET("mobilityModules/disciplines.json")
    suspend fun getMobilityModules(): List<MobilityModule>

    @GET("electives")
    suspend fun getElectives(@Query("groupName") name: String): List<Elective>
}

object Network {
    val api: Api by lazy { retrofit.create(Api::class.java) }
}