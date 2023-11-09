package com.example.weatherforecast.domain

import com.example.weatherforecast.data.Repository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class RepositoryStatusCollectingUseCase @Inject constructor(
    private val repository: Repository
) {
    fun execute(): StateFlow<String?> {
        return repository.repoStatusFlow
    }
}