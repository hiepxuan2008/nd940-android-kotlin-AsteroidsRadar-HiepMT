package com.udacity.asteroidradar.network

import com.udacity.asteroidradar.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidApi {
    @GET("neo/rest/v1/feed")
    suspend fun getNearEarthObjects(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): String
}


