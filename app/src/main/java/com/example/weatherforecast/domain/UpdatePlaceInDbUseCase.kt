package com.example.weatherforecast.domain

import com.example.weatherforecast.data.Repository
import com.example.weatherforecast.entity.Place
import javax.inject.Inject

class UpdatePlaceInDbUseCase @Inject constructor(
    private val repository: Repository
){
    suspend fun execute(placeId: Int, place: Place) {
        return repository.updatePlaceInDb(placeId, place)
    }
}