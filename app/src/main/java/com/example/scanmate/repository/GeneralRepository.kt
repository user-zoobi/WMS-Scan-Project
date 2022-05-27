package com.example.scanmate.repository

import com.example.scanmate.data.api.RetrofitClient
import com.example.scanmate.data.response.LoginResponse
import com.example.scanmate.data.response.UserLocationResponse
import com.example.scanmate.data.response.UserMenuResponse
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

    override suspend fun userMenu(
        UserNo: RequestBody,
        LocationNo: RequestBody
    ): List<UserMenuResponse> {
        return RetrofitClient.apiservice.userMenu(UserNo, LocationNo)
    }
}