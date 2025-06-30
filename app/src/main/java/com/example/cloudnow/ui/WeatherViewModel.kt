package com.example.cloudnow.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cloudnow.data.Constant
import com.example.cloudnow.network.NetworkResponse
import com.example.cloudnow.network.WeatherModel
import com.example.cloudnow.network.WeatherRetrofit
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    // Variable for getting the integrated weather data from retrofit.
    val weatherApi = WeatherRetrofit.weatherApi

    private val _weatherData = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherData : LiveData<NetworkResponse<WeatherModel>> = _weatherData

    fun getCity(city : String) {
        _weatherData.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = weatherApi.getWeather(
                    key = Constant.APIKEY,
                    city = city
                )
                if(response.isSuccessful) {
                    response.body().let {
                        _weatherData.value = NetworkResponse.Success(it!!)
                    }
                }else {
                    _weatherData.value = NetworkResponse.Error("Something went wrong")
                }
            }
            catch (e : Exception) {
                _weatherData.value = NetworkResponse.Error("Network connection error!")
            }
        }
    }
}