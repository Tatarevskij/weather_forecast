package com.example.weatherforecast.data

import androidx.room.*
import com.example.weatherforecast.entity.NewPlaceInRepo
import com.example.weatherforecast.entity.PlaceInRepo
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {
    @Transaction
    @Query("SELECT * FROM places ORDER BY name")
    fun getAll(): Flow<List<PlaceInRepo>>

    @Transaction
    @Query("DELETE FROM places")
    suspend fun deleteAll()

    @Query("SELECT * FROM places WHERE name LIKE :currentName")
    suspend fun getPlace(currentName: String): PlaceInRepo

    @Insert(
        entity = PlaceInRepo::class,
        onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(place: NewPlaceInRepo)

    @Update
    suspend fun update(place: PlaceInRepo)
}
