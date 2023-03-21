package com.example.nyc_school_challenges.network

import com.example.nyc_school_challenges.model.School
import com.example.nyc_school_challenges.model.SAT
import retrofit2.http.GET

interface NYCOpenDataAPI {

    @GET("s3k6-pzi2.json")
    suspend fun highSchools(): List<School>
    @GET("f9bf-2cp4.json")
    suspend fun satScores(): List<SAT>

}