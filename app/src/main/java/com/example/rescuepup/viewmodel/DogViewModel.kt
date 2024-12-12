package com.example.rescuepup.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rescuepup.data.Dog
import com.example.rescuepup.data.DogRepository
import com.example.rescuepup.data.DogUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DogViewModel(private val dogRepository: DogRepository) : ViewModel() {
    private val _uiState = mutableStateOf(DogUiState())
    private val _selectedTab = mutableStateOf(0)

    val uiState: State<DogUiState> = _uiState
    val selectedTab: State<Int> = _selectedTab

    fun setSelectedTab(index: Int) {
        _selectedTab.value = index  // Update the selected tab index
    }

    // Trigger reset and repopulate
    fun resetAndRepopulateDatabase() {
        viewModelScope.launch {
            dogRepository.resetAndRepopulateDatabase()
            // After repopulating, fetch the dogs
            fetchDogs()
        }
    }

    private suspend fun fetchDogs() {
        // Collect dogs from the database after resetting
        dogRepository.getAllDogs().collect { dogs ->
            // Update the UI state with the collected list of dogs
            _uiState.value = _uiState.value.copy(dogList = dogs)
        }
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

    // Add a new dog to the database
    fun addDogs(dogs: List<Dog>) {
        viewModelScope.launch {
            try {
                dogRepository.insertAll(dogs)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }
    // Add a single dog (for convenience)
    fun addDog(dog: Dog) {
        addDogs(listOf(dog))
    }

}
