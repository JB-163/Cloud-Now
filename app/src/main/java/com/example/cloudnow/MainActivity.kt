package com.example.cloudnow

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.cloudnow.ui.AppBar
import com.example.cloudnow.ui.SearchDrawer
import com.example.cloudnow.ui.WeatherPage
import com.example.cloudnow.ui.WeatherViewModel
import com.example.cloudnow.ui.theme.CloudNowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CloudNowTheme {

                // ViewModel instance.
                val weatherModel = ViewModelProvider(this)[WeatherViewModel::class.java]

                // variable to handle the city name in App bar.
                var cityName by rememberSaveable {
                    mutableStateOf("")
                }

                // variable to handle exitcard.
                var showCard by remember {
                    mutableStateOf(false)
                }

                // Variable to handle the search drawer.
                var showSearch by remember {
                    mutableStateOf(false)
                }
                // variable to handle the keyboard.
                val keyboardController = LocalSoftwareKeyboardController.current
                // variable to handle the focus.
                val focusManager = LocalFocusManager.current

                BackHandler(
                    enabled = true
                ) {
                    showCard = true
                }

                Box(
                    modifier = Modifier.fillMaxSize()
                        // code for dismissing the keyboard when the user taps outside the text field.
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    keyboardController?.hide()
                                    focusManager.clearFocus()
                                }
                            )
                        }
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            AppBar(
                                cityName = cityName,
                                onSearchClick = {
                                    showSearch = true
                                }
                            )
                        }
                    ) { innerPadding ->
                        WeatherPage(
                            modifier = Modifier.padding(innerPadding),
                            viewModel = weatherModel
                        )
                    }

                    SearchDrawer(
                        isVisible = showSearch,
                        onClose = {
                            showSearch = false
                        },
                        viewModel = weatherModel,
                        onCitySearched = {
                            searchedCity -> cityName = searchedCity
                        }
                    )

                    if(showCard) {
                        ExitCard(
                            onDismiss = {showCard = false},
                            onExit = {
                                (applicationContext as Activity).finish()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ExitCard(
    onDismiss : () -> Unit,
    onExit : () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.size(height = 105.dp, width = 300.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(12.dp)
            ) {
               Text(
                   "Do you want to exit?",
                   color = Color.Black,
                   fontStyle = MaterialTheme.typography.headlineMedium.fontStyle,
                   fontSize = 16.sp,
                   fontWeight = FontWeight.Bold,
                   modifier = Modifier.padding(start = 4.dp, top = 4.dp)
               )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(start = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = onDismiss,
                    ) {
                       Text("No", color = Color(0xFF66BB6A), fontWeight = FontWeight.SemiBold)
                    }
                    TextButton(
                        onClick = onExit,
                    ) {
                        Text("Yes", color = Color(0xFFD32F2F), fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}