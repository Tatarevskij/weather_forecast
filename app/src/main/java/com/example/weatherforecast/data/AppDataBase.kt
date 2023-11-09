package com.example.weatherforecast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherforecast.entity.PlaceInRepo

@Database(entities = [PlaceInRepo::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getPlaceDao(): PlaceDao
}