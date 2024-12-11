package com.example.rescuepup.data

data class DogUiState(
    val dogList: List<Dog> = emptyList(),
    val favoriteDogs: List<Dog> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)