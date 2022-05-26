package com.example.scanmate.repository

import com.example.scanmate.storage.data.api.RetrofitClient
import com.example.scanmate.storage.data.response.LoginResponse
import com.example.scanmate.storage.data.response.UserLocationResponse
import okhttp3.RequestBody

interface ApiHelper {

    suspend fun userAuthLogin(
        UserID:RequestBody,
        Pwd:RequestBody
    ):List<LoginResponse> = RetrofitClient.apiservice.userAuthLogin(UserID, Pwd)

    suspend fun userLocation(
        UserNo:RequestBody
    ):List<UserLocationResponse> = RetrofitClient.apiservice.userLocation(UserNo)
}