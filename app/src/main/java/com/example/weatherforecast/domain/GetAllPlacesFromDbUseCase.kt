package com.example.weatherforecast.domain

import com.example.weatherforecast.data.Repository
import com.example.weatherforecast.entity.PlaceInRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllPlacesFromDbUseCase @Inject constructor(
    private val repository: Repository
) {
    fun execute(): Flow<List<PlaceInRepo>> {
        return repository.getAllPlacesFromDb()
    }
}