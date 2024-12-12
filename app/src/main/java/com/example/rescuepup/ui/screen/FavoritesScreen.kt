package com.example.rescuepup.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rescuepup.data.Dog
import com.example.rescuepup.ui.theme.rubik_reg
import com.example.rescuepup.viewmodel.DogViewModel

@Composable
fun FavoritesScreen(viewModel: DogViewModel) {
    val uiState by viewModel.uiState

    Box(modifier = Modifier
        .fillMaxSize()
    ) {
        if (uiState.favoriteDogs.isEmpty()) {
            NoFavoritesAvailable()
        } else {
            FavoriteDogList(dogs = uiState.favoriteDogs, viewModel = viewModel)
        }

        uiState.errorMessage?.let {
            ErrorMessage(it)
        }
    }
}

@Composable
fun NoFavoritesAvailable() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("No Favorite Dogs Available", style = TextStyle(
            fontFamily = rubik_reg, fontSize = 40.sp, color = Color.Black), modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun FavoriteDogList(dogs: List<Dog>, viewModel: DogViewModel) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 76.dp)) {
        items(dogs) { dog ->
            FavoriteDogItem(dog = dog, viewModel = viewModel)
        }
    }
}

@Composable
fun FavoriteDogItem(dog: Dog, viewModel: DogViewModel) {
    val isFavorite = dog in viewModel.uiState.value.favoriteDogs

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = dog.imageResId),
            contentDescription = "${dog.name}'s image",
            modifier = Modifier.size(164.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 26.dp)
                .background(Color.LightGray.copy(alpha = 0.5f))
        ) {
            Text(dog.name, style = TextStyle(fontFamily = rubik_reg, fontSize = 45.sp, color = Color.Black))
            Text(dog.breed, style = TextStyle(fontFamily = rubik_reg, fontSize = 25.sp, color = Color.Black))
            Text("${dog.age} years old", style = TextStyle(fontFamily = rubik_reg, fontSize = 25.sp, color = Color.Black))
            Text(dog.location, style = TextStyle(fontFamily = rubik_reg, fontSize = 25.sp, color = Color.Black))
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

