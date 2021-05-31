package com.udacity.asteroidradar.network

import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.models.PictureOfDay
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfDayApi {
    @GET("planetary/apod")
    suspend fun getAstronomyPictureOfTheDay(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): PictureOfDay
}