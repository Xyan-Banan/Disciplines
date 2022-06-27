package com.example.disciplines.data.source.network

import com.example.disciplines.BuildConfig
import com.example.disciplines.data.source.DisciplinesDataSource
import com.example.disciplines.data.models.Discipline
import com.example.disciplines.data.models.DisciplinesBundle
import com.example.disciplines.data.models.asBundlesList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DisciplinesRemoteDataSource @Inject constructor(private val api: API) : DisciplinesDataSource {
    //alternative to gson
//private val moshi = Moshi.Builder()
//    .add(KotlinJsonAdapterFactory())
//    .build()

//    private val retrofit = Retrofit.Builder()
//        .client(
//            OkHttpClient.Builder()
//                .addInterceptor(
//                    HttpLoggingInterceptor()
//                        .setLevel(HttpLoggingInterceptor.Level.BODY)
//                )
//                .build()
//        )
////    .addConverterFactory(MoshiConverterFactory.create(moshi))
//        .addConverterFactory(GsonConverterFactory.create())
//        .baseUrl(BuildConfig.BASE_URL)
//        .build()
//
//    private val api: API by lazy { retrofit.create(API::class.java) }

    override suspend fun getDisciplinesByChoice(groupName: String): List<DisciplinesBundle> {
        return api.getDisciplinesByChoice(groupName).asBundlesList()
    }

    override suspend fun getMobilityModules(groupName: String): List<Discipline.MobilityModule> {
        return api.getMobilityModules(groupName)
    }

    override suspend fun getElectives(groupName: String): List<Discipline.Elective> {
        return api.getElectives(groupName)
    }
}