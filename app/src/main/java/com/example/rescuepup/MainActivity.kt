package com.example.rescuepup

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rescuepup.data.DogRepository
import com.example.rescuepup.data.RescuePupDatabase
import com.example.rescuepup.ui.screen.DogScreen
import com.example.rescuepup.ui.screen.FavoritesScreen
import com.example.rescuepup.ui.screen.ListPupScreen
import com.example.rescuepup.ui.theme.RescuePupTheme
import com.example.rescuepup.ui.theme.lobster
import com.example.rescuepup.ui.theme.playwritings
import com.example.rescuepup.ui.theme.ribeye
import com.example.rescuepup.ui.theme.rubikxbold
import com.example.rescuepup.viewmodel.DogViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
//    private val dogViewModel: DogViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = RescuePupDatabase.getDatabase(applicationContext, scope = CoroutineScope(
            Dispatchers.Main + SupervisorJob()))
        val dogDao = database.dogDao()
        val dogRepository = DogRepository(dogDao)

        // Clear the database for debugging purposes
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                database.clearAllTables() // Clears all tables in the database
//                Log.d("MainActivity", "Database cleared successfully")
//            } catch (e: Exception) {
//                Log.e("MainActivity", "Error clearing database", e)
//            }
//        }

        // Initialize the DogViewModel
        val dogViewModel = DogViewModel(dogRepository)
        enableEdgeToEdge()
        setContent {
            RescuePupTheme {
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
                }
                MainScreen(viewModel = dogViewModel)
            }
        }
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d("MainActivity", "Orientation changed: ${newConfig.orientation}")
    }
}

@Composable
fun MainScreen(viewModel: DogViewModel) {
    // Use rememberSaveable to make selectedTab persist across configuration changes like orientation changes
    val selectedTab = rememberSaveable { mutableStateOf(0) } // Using rememberSaveable

    // Column to stack the title, content, and tabs
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Title at the top
        Text(
            text = "Rescue Pup",
            style = TextStyle(fontFamily = ribeye, fontSize = 62.sp),
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.CenterHorizontally)
        )

        // Content for the selected tab
        Box(
            modifier = Modifier
                .weight(1f) // This will push the tabs to the bottom of the screen
                .fillMaxSize()
        ) {
            // TabRow with dynamic background based on selected tab
            TabRow(
                selectedTabIndex = selectedTab.value,
                modifier = Modifier.fillMaxWidth()
            ) {
                val tabs = listOf("Find-a-Pup", "Fav Pups", "List-a-Pup")

                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab.value == index,
                        onClick = {
                            selectedTab.value = index // Mutate selectedTab
                            Log.d("MainScreen", "Tab selected: ${selectedTab.value}")
                        }, modifier = Modifier
                            .padding(3.dp) // Padding for each tab
                            .height(40.dp)
                            .background(
                                if (selectedTab.value == index) Color.Cyan else Color.LightGray, // Background color
                                shape = MaterialTheme.shapes.small // Rounded corners
                            ), // Set shape for rounded corners
                        text = {
                            Text(
                                text = title,
                                style = TextStyle(
                                    fontFamily = rubikxbold, fontSize = 17.sp, color = Color.Black
                                )
                            )
                        }
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // Display the content based on the selected tab
                when (selectedTab.value) {
                    0 -> DogScreen(viewModel)
                    1 -> FavoritesScreen(viewModel)
                    2 -> ListPupScreen(viewModel)
                }
            }

        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RescuePupTheme {
//        MainScreen()
    }
}