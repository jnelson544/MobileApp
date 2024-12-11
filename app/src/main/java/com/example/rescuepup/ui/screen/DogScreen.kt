package com.example.rescuepup.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rescuepup.data.Dog
import com.example.rescuepup.viewmodel.DogViewModel

// ui/DogScreen.kt
@Composable
fun DogScreen(viewModel: DogViewModel) {
    val uiState by viewModel.uiState

    // Display loading indicator when data is loading
    if (uiState.isLoading) {
        CircularProgressIndicator()
    } else {
        // Display error message if any
        uiState.errorMessage?.let {
            Text("Error: $it")
        }

        // Display the list of dogs
        LazyColumn {
            items(uiState.dogList) { dog ->
                DogItem(dog, viewModel)
            }
        }
    }
}

@Composable
fun DogItem(dog: Dog, viewModel: DogViewModel) {
    val isFavorite = dog in viewModel.uiState.value.favoriteDogs
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(dog.name)
            Text(dog.breed)
        }
        IconButton(
            onClick = {
                if (isFavorite) {
                    viewModel.removeFromFavorites(dog)
                } else {
                    viewModel.addToFavorites(dog)
                }
            }
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = if (isFavorite) "Remove from Favorites" else "Add to Favorites"
            )
        }
    }
}
