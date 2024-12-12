package com.example.rescuepup.data

import kotlinx.coroutines.flow.Flow

class DogRepository(private val dogDao: DogDao) {
    suspend fun getAllDogs(): Flow<List<Dog>> = dogDao.getAllDogs()

    suspend fun insertDog(dog: Dog) = dogDao.insertDog(dog)

    suspend fun insertAll(dogs: List<Dog>) = dogDao.insertAll(dogs)

    suspend fun deleteDog(dog: Dog) = dogDao.deleteDog(dog)
}
