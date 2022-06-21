package com.example.disciplines.data.source.network

import com.example.disciplines.BuildConfig
import com.example.disciplines.data.DisciplinesDataSource
import com.example.disciplines.data.model.Discipline
import com.example.disciplines.data.model.DisciplinesBundle
import com.example.disciplines.data.model.asBundlesList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

object DisciplinesRemoteDataSource : DisciplinesDataSource {
    //alternative to gson
//private val moshi = Moshi.Builder()
//    .add(KotlinJsonAdapterFactory())
//    .build()

    private interface Api {
        @GET("disciplinesByChoice/{groupName}")
        suspend fun getDisciplinesByChoice(@Path("groupName") name: String): List<List<Discipline.ByChoice>>

        @GET("mobilityModules/{groupName}")
        suspend fun getMobilityModules(@Path("groupName") name: String): List<Discipline.MobilityModule>

        @GET("electives/{groupName}")
        suspend fun getElectives(@Path("groupName") name: String): List<Discipline.Elective>
    }

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

    private val api: Api by lazy { retrofit.create(Api::class.java) }
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