package com.example.cloudnow.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.example.cloudnow.network.NetworkResponse

@Composable
fun WeatherPage(
    modifier : Modifier = Modifier,
    viewModel : WeatherViewModel
) {
    var weatherModel = viewModel.weatherData.observeAsState()

    Box(modifier = modifier.fillMaxSize()) {
        when(val response = weatherModel.value) {
            is NetworkResponse.Error -> {
                Text(response.message.toString())
            }
            NetworkResponse.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(60.dp),
                            color = Color(0xFF3ff1b5),
                            strokeWidth = 6.dp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Fetching Details..",
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.bodyLarge,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
            }
            is NetworkResponse.Success -> {
                Text(response.data.toString())
            }
            null -> {}
        }
    }
}