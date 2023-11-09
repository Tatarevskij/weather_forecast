package com.example.weatherforecast.data

import com.example.weatherforecast.entity.Current
import com.example.weatherforecast.entity.Location
import com.example.weatherforecast.entity.Place
import com.example.weatherforecast.entity.PlaceInRepo

class PlaceInRepoDto(
    val place: PlaceInRepo,
    override val location: Location = LocationDto(
        name = place.name,
        localTime = place.date),
    override val current: Current = CurrentDto(
        temp = place.temp)
) : Place {
    data class LocationDto(
        override val name: String,
        override val localTime: String
    ) : Location

    data class CurrentDto(
        override val temp: String
    ) : Current
}