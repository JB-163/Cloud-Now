package com.example.cloudnow.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// Api interface for getting the weather data.
interface WeatherApiInterface {
    @GET("/v1/current.json")
    suspend fun getWeather(
        @Query("key") key : String,
        @Query("q") city : String
    ) : Response<WeatherModel>
}