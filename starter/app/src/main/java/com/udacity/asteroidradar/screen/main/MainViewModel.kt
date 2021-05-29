package com.udacity.asteroidradar.screen.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.models.Asteroid
import com.udacity.asteroidradar.models.PictureOfDay
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch

enum class AsteroidApiFilter {
    SHOW_TODAY_ASTEROIDS,
    SHOW_WEEK_ASTEROIDS,
    SHOW_SAVED_ASTEROIDS
}

class MainViewModel(private val repository: AsteroidRepository, application: Application) :
    AndroidViewModel(application) {

    private val _showLoading = MutableLiveData<Boolean>(false)
    val showLoading: LiveData<Boolean>
        get() = _showLoading

    private val _showErrorMessage = MutableLiveData<String?>()
    val showErrorMessage: LiveData<String?>
        get() = _showErrorMessage

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _navigateToDetailScreen = MutableLiveData<Asteroid?>()
    val navigateToDetailScreen: LiveData<Asteroid?>
        get() = _navigateToDetailScreen

    private val asteroidFilter =
        MutableLiveData<AsteroidApiFilter>(AsteroidApiFilter.SHOW_SAVED_ASTEROIDS)

    val asteroids = Transformations.switchMap(asteroidFilter) {
        when (it) {
            AsteroidApiFilter.SHOW_TODAY_ASTEROIDS -> repository.todayAsteroids
            AsteroidApiFilter.SHOW_WEEK_ASTEROIDS -> repository.weeklyAsteroids
            else -> repository.allSavedAsteroids
        }
    }


    init {
        viewModelScope.launch {
            try {
                _showLoading.value = true

                repository.refreshAsteroids()
                fetchPictureOfDayImageType()
            } catch (cause: Throwable) {
                // Handle error exception
                cause.printStackTrace()

                _showErrorMessage.value = application.getString(R.string.cant_refresh_data_error_message)
            } finally {
                _showLoading.value = false
            }
        }
    }

    private suspend fun fetchPictureOfDayImageType() {
        val result = repository.fetchPictureOfDay()
        if (result.mediaType == "image") {
            _pictureOfDay.value = repository.fetchPictureOfDay()
        }
    }

    fun onClickAsteroidItem(asteroid: Asteroid) {
        _navigateToDetailScreen.value = asteroid
    }

    fun updateFilter(asteroidApiFilter: AsteroidApiFilter) {
        asteroidFilter.value = asteroidApiFilter
    }

    fun onNavigateToDetailScreenCompleted() {
        _navigateToDetailScreen.value = null
    }

    fun onShowErrorMessageCompleted() {
        _showErrorMessage.value = null
    }
}