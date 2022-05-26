package com.example.scanmate.repository

import com.example.scanmate.storage.data.api.RetrofitClient
import com.example.scanmate.storage.data.response.LoginResponse
import com.example.scanmate.storage.data.response.UserLocationResponse
import okhttp3.RequestBody

class GeneralRepository :ApiHelper{

    override suspend fun userAuthLogin(
        UserID: RequestBody, Pwd: RequestBody
    ): List<LoginResponse> = RetrofitClient.apiservice.userAuthLogin(UserID, Pwd)

    override suspend fun userLocation(
        UserNo: RequestBody
    ): List<UserLocationResponse> {
        return RetrofitClient.apiservice.userLocation(UserNo)
    }
}