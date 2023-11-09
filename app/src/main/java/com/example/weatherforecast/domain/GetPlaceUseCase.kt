package com.example.weatherforecast.domain

import com.example.weatherforecast.data.Repository
import com.example.weatherforecast.entity.Place
import javax.inject.Inject

class GetPlaceUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun execute(name: String): Place?{
        return repository.getPlace(name)
    }
}
