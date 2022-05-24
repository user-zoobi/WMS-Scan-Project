package com.example.scanmate.data.api

import com.example.scanmate.data.response.LoginResponse
import com.example.scanmate.util.Constants.EndPoint.userAuth
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @POST(userAuth)
    @Multipart
    suspend fun userAuthLogin(
        @Part("UserID") UserID:RequestBody,
        @Part("Pwd") Pwd:RequestBody
    ): LoginResponse

}