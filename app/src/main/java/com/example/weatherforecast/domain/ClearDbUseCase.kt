package com.example.weatherforecast.domain

import com.example.weatherforecast.data.Repository
import javax.inject.Inject

class ClearDbUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun execute() {
        repository.clearDb()
    }
}