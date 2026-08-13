package com.example.dpdetectorapplication.data.database

import androidx.room3.Database
import androidx.room3.RoomDatabase

@Database(
    entities = [DetectieEntity::class],
    version = 1
)
abstract class DetectieDatabase : RoomDatabase() {

    abstract val detectieDao: DetectieDao

    companion object {
        const val DATABASE_NAME = "detecties_db"
    }
}