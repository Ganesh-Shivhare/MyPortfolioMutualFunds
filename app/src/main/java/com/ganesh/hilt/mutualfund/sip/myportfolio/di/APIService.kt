package com.ganesh.hilt.mutualfund.sip.myportfolio.di

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET("mf/search")
    fun getMFNameResults(
        @Query("q") q: String,
    ): Call<ArrayList<MFSchemeNameModel>>

    @GET("mf/{schemaCode}")
    fun getSchemeResults(
        @Path("schemaCode") q: String,
    ): Call<MFSchemeDataModel>
}