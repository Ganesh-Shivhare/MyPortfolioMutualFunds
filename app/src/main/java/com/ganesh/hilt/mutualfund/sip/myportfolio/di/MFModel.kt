package com.ganesh.hilt.mutualfund.sip.myportfolio.di

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MFSchemeNameModel(
    @SerializedName("schemeCode")
    @Expose
    val schemeCode: Int? = null,

    @SerializedName("schemeName")
    @Expose
    val schemeName: String? = null
)

data class MFSchemeDataModel(
    @SerializedName("meta")
    @Expose
    val schemeDetails: SchemeDetails? = null,

    @SerializedName("data")
    @Expose
    val data: List<SchemeData>? = null,

    @SerializedName("status")
    @Expose
    val status: String? = null
)

data class SchemeData(
    @SerializedName("date")
    @Expose
    val date: String? = null,

    @SerializedName("nav")
    @Expose
    val nav: String? = null
)


data class SchemeDetails(
    @SerializedName("fund_house")
    @Expose
    val fundHouse: String? = null,

    @SerializedName("scheme_type")
    @Expose
    val schemeType: String? = null,

    @SerializedName("scheme_category")
    @Expose
    val schemeCategory: String? = null,

    @SerializedName("scheme_code")
    @Expose
    val schemeCode: Int? = null,

    @SerializedName("scheme_name")
    @Expose
    val schemeName: String? = null,

    @SerializedName("isin_growth")
    @Expose
    val isinGrowth: Any? = null,

    @SerializedName("isin_div_reinvestment")
    @Expose
    val isinDivReinvestment: Any? = null
)

