package com.example.rescuepup.data

import android.adservices.adid.AdId
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dogs") // Define the table name
data class Dog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Auto-generate ID for each dog
    val name: String,
    val breed: String,
    val age: Int,
    val location: String,
    val imageResId: Int,
//    var isFavorite: Boolean = false // Track favorite status
)

