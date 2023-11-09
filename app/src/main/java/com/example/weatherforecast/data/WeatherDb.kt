package com.example.weatherforecast.data

import com.example.weatherforecast.entity.NewPlaceInRepo
import com.example.weatherforecast.entity.Place
import com.example.weatherforecast.entity.PlaceInRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDb @Inject constructor(
    private val placeDao: PlaceDao
) {
    fun getPlaces(): Flow<List<PlaceInRepo>> {
        return placeDao.getAll()
    }
    suspend fun getPlaceByName(name: String): Place {
        return PlaceInRepoDto(placeDao.getPlace(name))
    }

    suspend fun addPlace(newPlace: NewPlaceInRepo) {
        placeDao.insert(newPlace)
    }

    suspend fun updatePlace(place: PlaceInRepo){
        placeDao.update(place)
    }

    suspend fun deleteAll() {
        placeDao.deleteAll()
    }

}

