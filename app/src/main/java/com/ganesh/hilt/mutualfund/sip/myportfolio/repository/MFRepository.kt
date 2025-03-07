package com.ganesh.hilt.mutualfund.sip.myportfolio.repository

import android.util.Log
import com.ganesh.hilt.mutualfund.sip.myportfolio.di.APIService
import com.ganesh.hilt.mutualfund.sip.myportfolio.di.MFSchemeDataModel
import com.ganesh.hilt.mutualfund.sip.myportfolio.di.MFSchemeNameModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MFRepository @Inject constructor(private val apiService: APIService) {

    fun getMFNameResults(
        q: String, result: (Result<ArrayList<MFSchemeNameModel>>) -> Unit
    ) {
        apiService.getMFNameResults(q).enqueue(object : Callback<ArrayList<MFSchemeNameModel>?> {
            override fun onResponse(
                call: Call<ArrayList<MFSchemeNameModel>?>,
                response: Response<ArrayList<MFSchemeNameModel>?>
            ) {
                val responseData = response.body()
                result(Result.success(responseData as ArrayList<MFSchemeNameModel>))
            }

            override fun onFailure(call: Call<ArrayList<MFSchemeNameModel>?>, t: Throwable) {
                result(Result.failure(t))
            }
        })
    }

    fun getSchemeResults(q: String, result: (Result<MFSchemeDataModel>) -> Unit) {
        apiService.getSchemeResults(q).enqueue(object : Callback<MFSchemeDataModel> {
            override fun onResponse(
                call: Call<MFSchemeDataModel>, response: Response<MFSchemeDataModel>
            ) {
                val responseData = response.body()
                Log.d("TAG_scheme_details", "getSchemeResults:schemeCode $q")
                Log.d("TAG_scheme_details", "getSchemeResults: $responseData")
                responseData?.let {
                    result(Result.success(it))
                }
            }

            override fun onFailure(call: Call<MFSchemeDataModel>, t: Throwable) {
                result(Result.failure(t))
            }
        })
    }
}
