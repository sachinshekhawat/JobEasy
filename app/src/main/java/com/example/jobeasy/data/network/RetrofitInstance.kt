package com.example.jobeasy.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: JobApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://testapi.getlokalapp.com/") // Replace with real base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JobApiService::class.java)
    }
}