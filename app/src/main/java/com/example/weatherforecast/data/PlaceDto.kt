package com.example.weatherforecast.data

import com.example.weatherforecast.entity.Current
import com.example.weatherforecast.entity.Location
import com.example.weatherforecast.entity.Place
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import javax.inject.Inject

@JsonClass(generateAdapter = true)
data class PlaceDto @Inject constructor(
    @Json(name = "location") override val location: LocationDto,
    @Json(name = "current") override val current: CurrentDto
): Place {
    @JsonClass(generateAdapter = true)
    data class LocationDto(
        @Json(name = "name") override val name: String,
        @Json(name = "localtime") override val localTime: String
    ): Location
    @JsonClass(generateAdapter = true)
    data class CurrentDto(
        @Json(name = "temp_c") override val temp: String
    ):Current
}
