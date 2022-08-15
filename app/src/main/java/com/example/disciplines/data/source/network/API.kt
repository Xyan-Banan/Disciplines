package com.example.disciplines.data.source.network

import com.example.disciplines.data.models.Discipline
import retrofit2.http.GET
import retrofit2.http.Path

interface API {
    @GET("disciplinesByChoice/{groupName}")
    suspend fun getDisciplinesByChoice(@Path("groupName") name: String): List<List<Discipline.ByChoice>>

    @GET("mobilityModules/{groupName}")
    suspend fun getMobilityModules(@Path("groupName") name: String): List<Discipline.MobilityModule>

    @GET("electives/{groupName}")
    suspend fun getElectives(@Path("groupName") name: String): List<Discipline.Elective>
}