package com.example.scanmate.repository

import com.example.scanmate.data.api.RetrofitClient
import com.example.scanmate.data.response.LoginResponse
import com.example.scanmate.data.response.UserLocationResponse
import com.example.scanmate.data.response.UserMenuResponse
import okhttp3.RequestBody

interface ApiHelper {

    suspend fun userAuthLogin(
        UserID:RequestBody,
        Pwd:RequestBody
    ):List<LoginResponse> = RetrofitClient.apiservice.userAuthLogin(UserID, Pwd)

    suspend fun userLocation(
        UserNo:RequestBody
    ):List<UserLocationResponse> = RetrofitClient.apiservice.userLocation(UserNo)

    suspend fun userMenu(
        UserNo:RequestBody,
        LocationNo:RequestBody
    ):List<UserMenuResponse> = RetrofitClient.apiservice.userMenu(UserNo, LocationNo)
}