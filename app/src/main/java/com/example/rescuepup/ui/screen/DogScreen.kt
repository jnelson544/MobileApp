package com.example.rescuepup.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rescuepup.data.Dog
import com.example.rescuepup.ui.theme.lobster
import com.example.rescuepup.ui.theme.montserrat
import com.example.rescuepup.ui.theme.playwritings
import com.example.rescuepup.ui.theme.prefab
import com.example.rescuepup.ui.theme.rubik_black
import com.example.rescuepup.ui.theme.rubik_bold
import com.example.rescuepup.ui.theme.rubik_reg
import com.example.rescuepup.viewmodel.DogViewModel

// ui/DogScreen.kt
@Composable
fun DogScreen(viewModel: DogViewModel) {
    val uiState by viewModel.uiState
    val selectedTabIndex by viewModel.selectedTab

    // Show loading indicator while fetching data
    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        // Check if the dog list is empty
        if (uiState.dogList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No Dogs Available", modifier = Modifier.padding(16.dp))
            }
        } else {
                 //List of dogs
                LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 76.dp)) {
                    items(uiState.dogList) { dog ->
                        DogItem(dog = dog, viewModel = viewModel)
                    }
                }
        }
    }

    // Optionally, show an error message if there is one
    uiState.errorMessage?.let {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Error: $it", color = Color.Red, modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun DogItem(dog: Dog, viewModel: DogViewModel) {
    val isFavorite = dog in viewModel.uiState.value.favoriteDogs

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 6.dp, end = 6.dp, top = 36.dp), // Adjust padding as needed start = 6.dp, end = 36.dp, top = 36.dp
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = dog.imageResId),
            contentDescription = "${dog.name}'s image",
            modifier = Modifier.size(164.dp)
        )
        Column(
            modifier = Modifier.weight(1f).padding(start = 26.dp).background(Color.LightGray.copy(alpha = 0.5f))  // Adjust layout as necessary
        ) {
            Text(dog.name, style = TextStyle(
                fontFamily = rubik_reg, fontSize = 45.sp, color = Color.Black))
            Text(dog.breed, style = TextStyle(
                fontFamily = rubik_reg, fontSize = 25.sp, color = Color.Black))
            Text(text = "${dog.age} years old", style = TextStyle(
                fontFamily = rubik_reg, fontSize = 25.sp, color = Color.Black))
            Text(text = "${dog.location}", style = TextStyle(
                fontFamily = rubik_reg, fontSize = 25.sp, color = Color.Black))
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

