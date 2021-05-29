package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDatabaseDao {
    @Query("SELECT * FROM AsteroidDatabaseEntity ORDER BY date(closeApproachDate) ASC")
    fun getAllAsteroids(): LiveData<List<AsteroidDatabaseEntity>>

    @Query("SELECT * FROM AsteroidDatabaseEntity WHERE date(closeApproachDate)=date(:date)")
    fun getAsteroidsForADay(date: String): LiveData<List<AsteroidDatabaseEntity>>

    @Query("SELECT * FROM AsteroidDatabaseEntity WHERE date(closeApproachDate) BETWEEN date(:fromDate) AND date(:toDate) ORDER BY date(closeApproachDate) ASC")
    fun getAsteroidsInRange(fromDate: String, toDate: String): LiveData<List<AsteroidDatabaseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg videos: AsteroidDatabaseEntity)

    @Query("DELETE FROM AsteroidDatabaseEntity WHERE date(closeApproachDate) < date(:date)")
    suspend fun deleteAsteroidsPreviousDate(date: String)
}