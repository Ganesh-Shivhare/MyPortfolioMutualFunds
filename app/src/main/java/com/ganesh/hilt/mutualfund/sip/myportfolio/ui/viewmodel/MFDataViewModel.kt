package com.ganesh.hilt.mutualfund.sip.myportfolio.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ganesh.hilt.mutualfund.sip.myportfolio.di.MFSchemeDataModel
import com.ganesh.hilt.mutualfund.sip.myportfolio.di.MFSchemeNameModel
import com.ganesh.hilt.mutualfund.sip.myportfolio.repository.MFRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MFDataViewModel @Inject constructor(private val mfRepository: MFRepository) : ViewModel() {

    private val _searchResult: MutableLiveData<Result<ArrayList<MFSchemeNameModel>>> =
        MutableLiveData()
    val searchResult: LiveData<Result<ArrayList<MFSchemeNameModel>>> = _searchResult

    private val _schemeResult: MutableLiveData<Result<MFSchemeDataModel>> = MutableLiveData()
    val schemeResult: LiveData<Result<MFSchemeDataModel>> = _schemeResult

    fun getMFNameResults(q: String) {
        mfRepository.getMFNameResults(q) {
            _searchResult.postValue(it)
        }
    }

    suspend fun getSchemeResults(q: String) = mfRepository.getSchemeResults(q) {
        _schemeResult.postValue(it)
    }
}
