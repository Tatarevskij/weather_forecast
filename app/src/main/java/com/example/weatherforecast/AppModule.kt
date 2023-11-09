package com.example.weatherforecast

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.weatherforecast.data.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val SHARED_PREFS_NAME = "saved_names"


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationComponent (i.e. everywhere in the application)
    @Provides
    fun providePlaceDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "places_db"
    ).build() // The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun providePlaceDao(db: AppDatabase) =
        db.getPlaceDao() // The reason we can implement a Dao for the database

    @Singleton
    @Provides
    fun sharedPrefsProvider(
        @ApplicationContext app: Context
    ): SharedPreferences = app.getSharedPreferences(
        SHARED_PREFS_NAME,
        Context.MODE_PRIVATE
    )

    @Singleton
    @Provides
    fun provideRepository(db: AppDatabase): Repository =
        Repository(WeatherDb(db.getPlaceDao()), WeatherApi())

    @Singleton
    @Provides
    fun provideWeatherApi() = WeatherApi()
}