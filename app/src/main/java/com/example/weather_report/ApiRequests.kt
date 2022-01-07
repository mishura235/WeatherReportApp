package com.example.weather_report

import com.example.weather_report.api.Model
import retrofit2.Call
import retrofit2.http.GET

interface ApiRequests {
    @GET("weather?q=Volkhov&lang=RU&APPID=a7870ee693a097b5e8abf6644be2148d")
    fun GetWeather():Call<Model>

}