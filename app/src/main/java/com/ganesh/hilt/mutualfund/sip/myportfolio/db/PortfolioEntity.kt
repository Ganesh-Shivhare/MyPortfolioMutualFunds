package com.ganesh.hilt.mutualfund.sip.myportfolio.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "portfolio_entity")
data class PortfolioEntity(
    @SerializedName("id") @Expose @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @SerializedName("schemeCode") @Expose var schemeCode: String = "",
    @SerializedName("schemeName") @Expose var schemeName: String = "",
    @SerializedName("date") @Expose var date: Long = 0,
    @SerializedName("firstInvestment") @Expose var firstInvestment: Double = 0.0,
    @SerializedName("totalInvestment") @Expose var totalInvestment: Double = 0.0,
    @SerializedName("totalReturn") @Expose var totalReturn: Double = 0.0,
    @SerializedName("interestRate") @Expose var interestRate: Double = 0.0,
)
