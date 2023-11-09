package com.example.weatherforecast.domain

import com.example.weatherforecast.data.Repository
import com.example.weatherforecast.entity.Place
import javax.inject.Inject

class AddPlaceToDbUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun execute(newPlace: Place) {
        repository.addPlaceToDb(newPlace)
    }
}