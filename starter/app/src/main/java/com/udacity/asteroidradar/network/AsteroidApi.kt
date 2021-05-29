package com.udacity.asteroidradar.network

import com.udacity.asteroidradar.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidApi {
    @GET("neo/rest/v1/feed")
    suspend fun getNearEarthObjects(
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): String
}


