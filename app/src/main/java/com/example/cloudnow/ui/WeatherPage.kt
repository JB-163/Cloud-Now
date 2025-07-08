package com.example.cloudnow.ui

import androidx.annotation.ColorRes
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cloudnow.network.NetworkResponse
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import com.example.cloudnow.network.WeatherModel
import java.text.SimpleDateFormat
import java.util.Locale
import com.example.cloudnow.R

@Composable
fun WeatherPage(
    modifier : Modifier = Modifier,
    viewModel : WeatherViewModel
) {
    var weatherModel = viewModel.weatherData.observeAsState()

    Box(modifier = modifier
        .fillMaxSize()
        .background(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color("#59469d".toColorInt()),
                    Color("#643d67".toColorInt())
                )
            )
        )
    ) {
        when(val response = weatherModel.value) {
            is NetworkResponse.Error -> {
                Text(response.message.toString())
            }
            NetworkResponse.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f)),
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
                WeatherDetails(data = response.data)
            }
            null -> {}
        }
    }
}

@Composable
fun WeatherDetails(data : WeatherModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = data.current.condition.text,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 16.sp
        )
        AsyncImage(
            model = "https://${data.current.condition.icon}",
            contentDescription = "Image",
            modifier = Modifier
                .size(120.dp)
                .padding(top = 4.dp)
        )
        Text(
            text = formatDateTime(data.current.last_updated),
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign  = TextAlign.Center
        )
        Text(
            text = "${data.current.temp_c}°C",
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            textAlign  = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                WeatherCards(
                    value = data.current.humidity,
                    icon = R.drawable.humidity,
                    unit = "Humidity"
                )
                WeatherCards(
                    value = data.current.wind_kph,
                    icon = R.drawable.wind,
                    unit = "Wind"
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                WeatherCards(
                    value = data.current.cloud,
                    icon = R.drawable.cloudy,
                    unit = "Cloud"
                )
                WeatherCards(
                    value = data.current.uv,
                    icon = R.drawable.sunny,
                    unit = "UV"
                )
            }
        }

    }
}


@Composable
fun WeatherCards(value : String, unit : String, icon : Int) {
    Box(
        modifier = Modifier
            .size(height = 150.dp, width = 120.dp)
            .background(
                color = colorResource(id = R.color.purple),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = value,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = unit,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// A function to format the date and time.
fun formatDateTime(input : String) : String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val outputFormat = SimpleDateFormat("EEE MMM dd | hh:mm a", Locale.getDefault())
        val date = inputFormat.parse(input)
        outputFormat.format(date!!)
    } catch(e : Exception) {
        input
    }
}