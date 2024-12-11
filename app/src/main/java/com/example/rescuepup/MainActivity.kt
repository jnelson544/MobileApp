package com.example.rescuepup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rescuepup.ui.theme.RescuePupTheme
import com.example.rescuepup.ui.theme.ribeye

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RescuePupTheme {
                MainScreen()
                DogScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val tabs = listOf("All Dogs", "Favorites")
    var selectedTabIndex = 0 // Use remember and mutableStateOf to dynamically control tab selection

    // Column to stack the title, content, and tabs
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        // Title at the top
        Text(
            text = "Rescue Pup",
            style = TextStyle(fontFamily = ribeye, fontSize = 52.sp),
            modifier = Modifier
                .padding(42.dp)
                .align(Alignment.CenterHorizontally)

        )


        // Content for the selected tab
        Box(
            modifier = Modifier
                .weight(1f) // This will push the tabs to the bottom of the screen
                .fillMaxSize()
        ) {
            when (selectedTabIndex) {
                0 -> AllDogsScreen() // Placeholder for All Dogs screen
                1 -> FavoritesScreen() // Placeholder for Favorites screen
            }
        }

        // Tab Row with a border at the bottom
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, Color.Gray) // Border around the TabRow
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.fillMaxWidth(),
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = Color.Blue, // Custom color for the indicator
                        height = 4.dp // Custom height for the indicator
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                        },
                        modifier = Modifier
                            .padding(4.dp) // Padding for each tab
                            .background(
                                if (selectedTabIndex == index) Color.Cyan else Color.LightGray, // Background color
                                shape = MaterialTheme.shapes.small
                            ), // Set shape for rounded corners
                        text = {
                            Text(
                                text = title,
                                style = CustomTabTextStyle() // Applying custom font style
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CustomTabTextStyle(): TextStyle {
    return TextStyle(
        fontFamily = ribeye, // Replace with your font
        fontSize = 23.sp, // Change font size
        color = Color.Black // Set text color
    )
}

@Composable
fun DogScreen() {
//    dogList: List<Dog>
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Set the background image using the Image composable
        Image(
            painter = painterResource(id = R.drawable.paws),
            contentDescription = "Paw prints background",
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            contentScale = ContentScale.Crop // This ensures the image covers the entire background
        )

        // Your scrolling content goes here
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp) // Add padding to the content so it doesn't touch the edges
        ) {
//            items(dogList) { dog ->
//                DogItem(dog)
//            }
        }
    }
}



@Composable
fun AllDogsScreen() {
    // Display a list of all adoptable dogs
    Text(text = "All Dogs")
}

@Composable
fun FavoritesScreen() {
    // Display a list of favorite dogs
    Text(text = "Favorites")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RescuePupTheme {
        MainScreen()
    }
}