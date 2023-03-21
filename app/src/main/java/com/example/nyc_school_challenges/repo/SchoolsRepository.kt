package com.example.nyc_school_challenges.repo


import com.example.nyc_school_challenges.model.School
import com.example.nyc_school_challenges.model.SAT
import com.example.nyc_school_challenges.model.SchoolModel
import com.example.nyc_school_challenges.network.NYCOpenDataAPI

class SchoolsRepository(private val api: NYCOpenDataAPI) {

    private var highSchools: List<School>? = null
    private var sats: List<SAT>? = null

    fun fetchSchools(highSchools: List<School>?, sats: List<SAT>?): List<SchoolModel> {
        if(highSchools == null || sats == null) return listOf()
        val schoolsMap = mutableMapOf<String, SAT>()
        for (satScore in sats) {
            schoolsMap[satScore.dbn ?: ""] = satScore
        }
        return highSchools.map {
            SchoolModel(school = it, satScores = schoolsMap[it.dbn])
        }
    }

    suspend fun getSchoolModel(callback : SchoolsCallback){
        try {
            highSchools = api.highSchools()
            sats = api.satScores()
            val result = fetchSchools(highSchools, sats)
            if(result.isNotEmpty()){
                callback.onSuccess(result)
            }
        }
        catch (e: Exception){
            callback.onFailure(e)
        }
    }
    interface SchoolsCallback {
        fun onSuccess(schools: List<SchoolModel>)
        fun onFailure(e: Throwable)
    }
}
