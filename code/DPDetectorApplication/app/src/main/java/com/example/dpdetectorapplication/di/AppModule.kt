package com.example.dpdetectorapplication.di

import android.app.Application
import androidx.room3.Room
import com.example.dpdetectorapplication.data.database.DetectieDatabase
import com.example.dpdetectorapplication.data.repository.DetectieRepository
import com.example.dpdetectorapplication.data.repository.DetectieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDetectieDatabase(app: Application): DetectieDatabase {
        return Room.databaseBuilder(
            app,
            DetectieDatabase::class.java,
            DetectieDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideDetectieRepository(db: DetectieDatabase): DetectieRepository {
        return DetectieRepositoryImpl(db.detectieDao)
    }
}
