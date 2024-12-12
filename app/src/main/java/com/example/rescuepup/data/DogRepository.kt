package com.example.rescuepup.data

import com.example.rescuepup.R
import kotlinx.coroutines.flow.Flow

class DogRepository(private val dogDao: DogDao) {
    suspend fun getAllDogs(): Flow<List<Dog>> = dogDao.getAllDogs()

    suspend fun insertAll(dogs: List<Dog>) = dogDao.insertAll(dogs)

    suspend fun resetAndRepopulateDatabase() {
        // Clear all existing data
        dogDao.clearAllDogs()

        // Insert initial data
        val initialDogs = listOf(
            Dog(1, "Buddy", "Golden Retriever", 3, "Missoula", R.drawable.retrieve),
            Dog(2, "Bella", "Labrador", 2, "Ronan", R.drawable.lab),
            Dog(3, "Charlie", "Beagle", 4, "Missoula", R.drawable.beagle),
            Dog(4, "Daisy", "German Shepard", 3, "Missoula", R.drawable.shepard),
            Dog(5, "Luna", "Chihuahua", 4, "Polson", R.drawable.hua),
            Dog(6, "Cooper", "Poodle", 7, "Ronan", R.drawable.poodle),
            Dog(7, "Rosie", "Pitbull", 9, "Missoula", R.drawable.pitbull),
            Dog(8, "Perkins", "Papillon", 15, "Missoula", R.drawable.papillon),
            Dog(9, "Prince", "Bulldog", 1, "Kalispell", R.drawable.bulldog)

        )

        dogDao.insertAll(initialDogs)
    }
}

