package com.example.weather

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface ApiInterface {
    @GET("weather")
    fun getWeatherData(
        @Query("q") location: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String,
    ): Call<WeatherApp>

    @GET("forecast")
    fun getForecastData(
        @Query("q") location: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String,
    )
}