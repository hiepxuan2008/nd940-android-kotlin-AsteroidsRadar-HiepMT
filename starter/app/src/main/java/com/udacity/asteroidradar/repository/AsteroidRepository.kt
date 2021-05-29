package com.udacity.asteroidradar.repository

import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.ext.asDatabaseModel
import com.udacity.asteroidradar.ext.asDomainModel
import com.udacity.asteroidradar.models.PictureOfDay
import com.udacity.asteroidradar.network.AsteroidApi
import com.udacity.asteroidradar.network.PictureOfDayApi
import com.udacity.asteroidradar.util.Constants
import com.udacity.asteroidradar.util.NetworkUtils
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class AsteroidRepository(
    private val database: AsteroidDatabase,
    private val asteroidApi: AsteroidApi,
    private val pictureOfDayApi: PictureOfDayApi
) {
    private var today = ""
    private var next7Days = ""

    init {
        val dataFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        val calendar = Calendar.getInstance()
        today = dataFormat.format(calendar.time)

        calendar.add(Calendar.DAY_OF_YEAR, 7)
        next7Days = dataFormat.format(calendar.time)
    }

    suspend fun refreshAsteroids() {
        val jsonString = asteroidApi.getNearEarthObjects()
        val asteroids = NetworkUtils.parseAsteroidsJsonResult(JSONObject(jsonString))
        database.dao.insertAll(*asteroids.asDatabaseModel())
    }

    suspend fun fetchPictureOfDay(): PictureOfDay {
        return pictureOfDayApi.getAstronomyPictureOfTheDay()
    }

    suspend fun deleteAsteroidsFromPreviousDay() {
        val calendar = Calendar.getInstance()
        val dataFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        val today = dataFormat.format(calendar.time)
        database.dao.deleteAsteroidsPreviousDate(today)
    }

    val todayAsteroids = Transformations.map(database.dao.getAsteroidsForADay(today)) {
        it.asDomainModel()
    }

    val weeklyAsteroids = Transformations.map(database.dao.getAsteroidsInRange(today, next7Days)) {
        it.asDomainModel()
    }

    val allSavedAsteroids = Transformations.map(database.dao.getAllAsteroids()) {
        it.asDomainModel()
    }
}