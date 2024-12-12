package com.example.rescuepup.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

@Dao
interface DogDao {
    // Insert a dog into the database
    @Insert
    suspend fun insertDog(dog: Dog)

    @Insert
    suspend fun insertAll(dogs: List<Dog>)

    // Retrieve all dogs from the database
    @Query("SELECT * FROM dogs")
    fun getAllDogs(): Flow<List<Dog>>

    // Delete a specific dog from the database
    @Delete
    suspend fun deleteDog(dog: Dog)
}
