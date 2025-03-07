package com.ganesh.hilt.mutualfund.sip.myportfolio.repository

import com.ganesh.hilt.mutualfund.sip.myportfolio.db.PortfolioDao
import com.ganesh.hilt.mutualfund.sip.myportfolio.db.PortfolioEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PortfolioRepository @Inject constructor(private val dao: PortfolioDao) {

    fun getAllPortfolio(): Flow<List<PortfolioEntity>> = dao.getAllPortfolio()

    suspend fun insertPortfolio(portfolioEntity: PortfolioEntity) {
        dao.insertPortfolio(portfolioEntity)
    }
}
