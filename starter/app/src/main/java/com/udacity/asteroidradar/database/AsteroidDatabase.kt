package com.udacity.asteroidradar.database

import android.content.Context
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.room.*

@Database(entities = [AsteroidDatabaseEntity::class], version = 1, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val dao: AsteroidDatabaseDao

    companion object {
        @Volatile
        private lateinit var INSTANCE: AsteroidDatabase

        fun getInstance(context: Context): AsteroidDatabase {
            synchronized(AsteroidDatabase::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDatabase::class.java,
                        "asteroids"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}


