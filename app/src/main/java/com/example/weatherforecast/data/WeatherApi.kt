package com.example.weatherforecast.data

import com.example.weatherforecast.entity.Place
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

private const val BASE_URL = "https://api.weatherapi.com/v1/"
private const val API_KEY = "e34edf38062046eb8b2135246230903"

class WeatherApi @Inject constructor() {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val searchPlaceApi: SearchPlaceApi = retrofit.create(
        SearchPlaceApi::class.java
    )

    suspend fun getPlace(name:String): Place{
        return this.searchPlaceApi.getPlaceFromDto(name)
    }
}

interface SearchPlaceApi {
    @GET("current.json?key=$API_KEY&aqi=no")
    suspend fun getPlaceFromDto(
        @Query("q") name: String
    ): PlaceDto
}
