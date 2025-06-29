package com.example.cloudnow.ui

import android.util.Log
import androidx.lifecycle.ViewModel

class WeatherViewModel : ViewModel() {

    fun getCity(city : String) {
        Log.i("city", city)
    }
}