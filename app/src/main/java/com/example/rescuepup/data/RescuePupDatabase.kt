package com.example.rescuepup.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabase.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.rescuepup.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Define the Room database class
@Database(entities = [Dog::class], version = 1)
abstract class RescuePupDatabase : RoomDatabase() {

    // Abstract method to get the DAO
    abstract fun dogDao(): DogDao

    // Companion object to instantiate the database
    companion object {
        @Volatile
        private var INSTANCE: RescuePupDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): RescuePupDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RescuePupDatabase::class.java,
                    "rescue_pup_database"
                )
                    .addCallback(DogDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // Move populateDatabase here
        suspend fun populateDatabase(dogDao: DogDao) {
            val dogs = listOf(
                Dog(0, "Max", "Bulldog", 5, "New York", R.drawable.bulldog),
                Dog(0, "Bella", "Labrador", 3, "Los Angeles", R.drawable.lab),
                Dog(0, "Charlie", "Beagle", 4, "Chicago", R.drawable.beagle)
            )
            dogDao.insertAll(dogs)
        }
    }


    // Callback to populate the database
    private class DogDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    try {
                        populateDatabase(database.dogDao())
                        Log.d("RescuePupDatabase", "Database populated successfully")
                    } catch (e: Exception) {
                        Log.e("RescuePupDatabase", "Error populating database", e)
                    }
                }
            }
        }
    }
}

