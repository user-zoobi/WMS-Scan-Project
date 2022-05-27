package com.example.scanmate.data.api

import com.example.scanmate.data.routes.Routes.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiservice:ApiService by lazy {

        retrofit.create(ApiService::class.java)
    }
}