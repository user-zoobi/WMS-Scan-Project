package com.example.scanmate.storage.data.api

import com.example.scanmate.storage.data.response.LoginResponse
import com.example.scanmate.storage.data.response.UserLocationResponse
import com.example.scanmate.util.Constants.EndPoint.userAuth
import com.example.scanmate.util.Constants.EndPoint.userLoc
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST(userAuth)
    suspend fun userAuthLogin(
        @Part("UserID") UserID: RequestBody,
        @Part("Pwd") Pwd: RequestBody
    ): List<LoginResponse>

    @Multipart
    @POST(userLoc)
    suspend fun userLocation(
        @Part("UserNo") UserNo:RequestBody
    ):List<UserLocationResponse>
}