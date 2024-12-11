package com.example.rescuepup.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rescuepup.data.Dog
import com.example.rescuepup.data.DogUiState
import kotlinx.coroutines.launch

class DogViewModel : ViewModel() {
    private val _uiState = mutableStateOf(DogUiState())
    val uiState: State<DogUiState> = _uiState

    // Simulating loading data
    fun loadDogs() {
        viewModelScope.launch {
            _uiState.value = DogUiState(isLoading = true) // Show loading
            try {
                // Simulate data fetch
                val dogs = fetchDogsFromDatabase() // Replace with real database call
                _uiState.value = DogUiState(dogList = dogs) // Update UIState with dogs
            } catch (e: Exception) {
                _uiState.value = DogUiState(errorMessage = e.message)
            }
        }
    }

    // Simulate fetching dogs from a repository/database
    private suspend fun fetchDogsFromDatabase(): List<Dog> {
        // In a real app, you would call your repository or Room DB here
        return listOf(
            Dog(1, "Max", "Bulldog", 5, "https://example.com/max.jpg"),
            Dog(2, "Bella", "Labrador", 3, "https://example.com/bella.jpg")
        )
    }

    // Function to add a dog to the favorites
    fun addToFavorites(dog: Dog) {
        val updatedFavorites = _uiState.value.favoriteDogs.toMutableList()
        updatedFavorites.add(dog)
        _uiState.value = _uiState.value.copy(favoriteDogs = updatedFavorites)
    }

    // Function to remove a dog from the favorites
    fun removeFromFavorites(dog: Dog) {
        val updatedFavorites = _uiState.value.favoriteDogs.toMutableList()
        updatedFavorites.remove(dog)
        _uiState.value = _uiState.value.copy(favoriteDogs = updatedFavorites)
    }
}