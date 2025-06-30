package com.example.cloudnow.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Retrofit instance for getting the weather data.
object WeatherRetrofit {

    private const val BASE_URL = "https://api.weatherapi.com"
    private fun getInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Api Interface instance that is integrated with retrofit.
    val weatherApi : WeatherApiInterface = getInstance().create(WeatherApiInterface::class.java)
}