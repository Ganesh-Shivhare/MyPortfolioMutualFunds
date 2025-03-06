package com.ganesh.hilt.mutualfund.sip.myportfolio.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ganesh.hilt.mutualfund.sip.myportfolio.db.PortfolioEntity
import com.ganesh.hilt.mutualfund.sip.myportfolio.repository.PortfolioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PortfolioDataBaseViewModel @Inject constructor(private val repository: PortfolioRepository) :
    ViewModel() {

    val notifications: LiveData<List<PortfolioEntity>> =
        repository.getAllPortfolio().asLiveData()

    fun insertPortfolio(portfolioEntity: PortfolioEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertPortfolio(portfolioEntity)
        }
    }
}
