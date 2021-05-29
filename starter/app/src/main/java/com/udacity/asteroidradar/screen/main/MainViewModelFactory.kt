package com.udacity.asteroidradar.screen.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.repository.AsteroidRepository

class MainViewModelFactory(
    private val repository: AsteroidRepository,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}