package com.ganesh.hilt.mutualfund.sip.myportfolio.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PortfolioEntity::class], version = 1, exportSchema = false)
abstract class PortfolioDatabase : RoomDatabase() {
    abstract fun notificationDao(): PortfolioDao
}
