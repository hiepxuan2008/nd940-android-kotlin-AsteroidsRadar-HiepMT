package com.udacity.asteroidradar.network

import com.udacity.asteroidradar.models.PictureOfDay
import com.udacity.asteroidradar.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfDayApi {
    @GET("planetary/apod")
    suspend fun getAstronomyPictureOfTheDay(
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): PictureOfDay
}