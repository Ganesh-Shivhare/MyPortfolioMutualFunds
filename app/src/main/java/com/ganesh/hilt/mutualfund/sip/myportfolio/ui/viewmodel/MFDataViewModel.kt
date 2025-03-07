package com.ganesh.hilt.mutualfund.sip.myportfolio.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganesh.hilt.mutualfund.sip.myportfolio.db.PortfolioEntity
import com.ganesh.hilt.mutualfund.sip.myportfolio.di.MFSchemeDataModel
import com.ganesh.hilt.mutualfund.sip.myportfolio.di.MFSchemeNameModel
import com.ganesh.hilt.mutualfund.sip.myportfolio.di.SchemeData
import com.ganesh.hilt.mutualfund.sip.myportfolio.repository.MFRepository
import com.ganesh.hilt.mutualfund.sip.myportfolio.utils.formatDate
import com.ganesh.hilt.mutualfund.sip.myportfolio.utils.keep2DecimalPoints
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class MFDataViewModel @Inject constructor(private val mfRepository: MFRepository) : ViewModel() {

    private val _searchResult: MutableLiveData<Result<ArrayList<MFSchemeNameModel>>> =
        MutableLiveData()
    val searchResult: LiveData<Result<ArrayList<MFSchemeNameModel>>> = _searchResult

    private val _schemeResult: MutableLiveData<Result<MFSchemeDataModel>> = MutableLiveData()
    val schemeResult: LiveData<Result<MFSchemeDataModel>> = _schemeResult

    fun getMFNameResults(q: String) {
        viewModelScope.launch {
            mfRepository.getMFNameResults(q) {
                _searchResult.postValue(it)
            }
        }
    }

    fun getSchemeResults(q: PortfolioEntity, result: (Result<MFSchemeDataModel>) -> Unit) {
        viewModelScope.launch {
            mfRepository.getSchemeResults(q.schemeCode) {
                _schemeResult.postValue(it)
                result(it)
            }
        }
    }

    fun getMonthlyNAVSum(
        navList: List<SchemeData>, portfolioEntity: PortfolioEntity
    ): PortfolioEntity {
        val selectedDate = portfolioEntity.date.formatDate() // Convert Long to String
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val selectedDay = selectedDate.substring(0, 2).toInt()
        val selectedMonthYear = selectedDate.substring(3) // Extract MM-yyyy

        // Group NAV data by MM-yyyy, ensuring only dates on or after the selected date are included
        val groupedByMonth = navList.filter {
            it.date?.let { d ->
                dateFormat.parse(d)
                    ?.equals(dateFormat.parse(selectedDate)) == true || dateFormat.parse(d)
                    ?.after(dateFormat.parse(selectedDate)) ?: false
            } ?: false
        }.groupBy { it.date?.substring(3) } // Extract MM-yyyy for grouping

        var totalNAV = 0.0
        var numberOfPayments = 0.0
        var todayUnit = 0.0
        var isFirstEntry = true

        groupedByMonth.forEach { (monthYear, navEntries) ->
            val sortedDates = navEntries.sortedBy {
                it.date?.let { d ->
                    dateFormat.parse(d)
                }
            } // Sort by date

            // For the first month, only consider dates **on or after** the selected date
            val filteredEntries = if (monthYear == selectedMonthYear) {
                sortedDates.filter {
                    it.date!!.substring(0, 2).toInt() >= selectedDay
                }
            } else {
                sortedDates
            }

            // Find exact match or next available date
            val closestNav =
                filteredEntries.find { it.date!!.substring(0, 2).toInt() == selectedDay }
                    ?: filteredEntries.find { it.date!!.substring(0, 2).toInt() > selectedDay }
                    ?: sortedDates.firstOrNull() // Fallback to first available date if none match

            closestNav?.let {
                if (it.date!!.substring(0, 2).toInt() >= selectedDay) {
                    if (isFirstEntry) {
                        todayUnit = it.nav
                        isFirstEntry = false
                    }
                    numberOfPayments++
                    totalNAV += (portfolioEntity.firstInvestment / it.nav).keep2DecimalPoints()
                }
            }
        }

        // Round totalNAV to 2 decimal places
        totalNAV = totalNAV.keep2DecimalPoints()

        portfolioEntity.totalInvestment = numberOfPayments * portfolioEntity.firstInvestment
        if (navList.isNotEmpty()) {
            portfolioEntity.totalReturn = totalNAV * navList[0].nav
        }

        return portfolioEntity
    }
}
