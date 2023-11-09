package com.example.weatherforecast.entity

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class NewPlaceInRepo(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "temp")
    val temp: String
)
