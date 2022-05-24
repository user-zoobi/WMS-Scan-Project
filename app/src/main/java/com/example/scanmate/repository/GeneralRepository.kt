package com.example.scanmate.repository

import com.example.scanmate.data.api.RetrofitClient
import com.example.scanmate.data.response.LoginResponse
import okhttp3.RequestBody

class GeneralRepository :ApiHelper{
    override suspend fun userAuthLogin(
        UserID: RequestBody, Pwd: RequestBody
    ): String = RetrofitClient.apiservice.userAuthLogin(UserID, Pwd)
}