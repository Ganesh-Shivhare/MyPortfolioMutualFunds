package com.ganesh.hilt.mutualfund.sip.myportfolio.di

import android.content.Context
import androidx.room.Room
import com.ganesh.hilt.mutualfund.sip.myportfolio.db.PortfolioDao
import com.ganesh.hilt.mutualfund.sip.myportfolio.db.PortfolioDatabase
import com.ganesh.hilt.mutualfund.sip.myportfolio.repository.PortfolioRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PortfolioDatabase {
        return Room.databaseBuilder(
            context,
            PortfolioDatabase::class.java,
            "notifications_db"
        ).allowMainThreadQueries().build()
    }

    @Provides
    fun provideDao(database: PortfolioDatabase): PortfolioDao {
        return database.notificationDao()
    }

    @Provides
    fun provideRepository(dao: PortfolioDao): PortfolioRepository {
        return PortfolioRepository(dao)
    }

    @Provides
    fun provideBaseUrl(): String = "https://api.mfapi.in/"

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): APIService = retrofit.create(APIService::class.java)

}
