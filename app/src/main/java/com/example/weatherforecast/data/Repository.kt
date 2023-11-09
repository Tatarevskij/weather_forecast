package com.example.weatherforecast.data

import android.content.SharedPreferences
import com.example.weatherforecast.entity.NewPlaceInRepo
import com.example.weatherforecast.entity.Place
import com.example.weatherforecast.entity.PlaceInRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class Repository @Inject constructor(
    private val weatherDb: WeatherDb,
    private val weatherApi: WeatherApi,
) {
    private val _repoStatusFlow = MutableStateFlow<String?>(null)
    val repoStatusFlow = _repoStatusFlow.asStateFlow()

    suspend fun getPlace(name: String): Place? {
        return when{
            getFromApi(name) != null -> getFromApi(name)!!
            getFromDb(name) != null -> getFromDb(name)!!
            else -> {
                _repoStatusFlow.emit("No data was found")
                null
            }
        }
    }

    private suspend fun getFromApi(name: String): Place? {
        _repoStatusFlow.emit("trying to load from internet")
        _repoStatusFlow.emit(null)
        return try {
            weatherApi.getPlace(name)
        } catch (ex: Exception) {
            _repoStatusFlow.emit(ex.message.toString())
            null
        }
    }

    private suspend fun getFromDb(name: String): Place? {
        _repoStatusFlow.emit("trying to load from internal storage")
        _repoStatusFlow.emit(null)
        return try {
            weatherDb.getPlaceByName(name)
        } catch (ex: Exception) {
            null
        }
    }

    suspend fun addPlaceToDb(newPlace: Place) {
        val  newPlaceInRepoDto = NewPlaceInRepo(
            name = newPlace.location.name,
            date = newPlace.location.localTime,
            temp = newPlace.current.temp
        )
        weatherDb.addPlace(newPlaceInRepoDto)
    }

    suspend fun clearDb() {
        weatherDb.deleteAll()
    }

    fun getAllPlacesFromDb(): Flow<List<PlaceInRepo>> {
        return weatherDb.getPlaces()
    }

    suspend fun updatePlaceInDb(placeId: Int, place: Place) {
        val placeUpdated = PlaceInRepo(
            id = placeId,
            name = place.location.name,
            date = place.location.localTime,
            temp = place.current.temp
        )
        return weatherDb.updatePlace(placeUpdated)
    }
}