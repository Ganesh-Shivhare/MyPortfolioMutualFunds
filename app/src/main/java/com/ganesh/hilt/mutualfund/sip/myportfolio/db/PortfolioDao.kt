package com.ganesh.hilt.mutualfund.sip.myportfolio.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PortfolioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPortfolio(portfolioEntity: PortfolioEntity): Long // Return inserted row ID

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotifications(portfolioEntityList: List<PortfolioEntity>): List<Long>

    @Query("SELECT * FROM portfolio_entity ORDER BY id DESC")
    fun getAllPortfolio(): Flow<List<PortfolioEntity>>
}
