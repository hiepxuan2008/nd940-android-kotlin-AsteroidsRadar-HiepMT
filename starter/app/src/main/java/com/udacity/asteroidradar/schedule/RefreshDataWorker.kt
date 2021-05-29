package com.udacity.asteroidradar.schedule

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.network.NetworkApiProvider
import com.udacity.asteroidradar.repository.AsteroidRepository
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val database = AsteroidDatabase.getInstance(applicationContext)
        val repository = AsteroidRepository(
            database,
            NetworkApiProvider.asteroidApi,
            NetworkApiProvider.pictureOfDayApi
        )
        return try {
            repository.refreshAsteroids()
            repository.deleteAsteroidsFromPreviousDay()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }
}