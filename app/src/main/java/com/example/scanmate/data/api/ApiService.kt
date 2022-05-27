package com.example.scanmate.data.api

import com.example.scanmate.data.response.LoginResponse
import com.example.scanmate.data.response.UserLocationResponse
import com.example.scanmate.data.response.UserMenuResponse
import com.example.scanmate.data.routes.Routes.EndPoint.userAuth
import com.example.scanmate.data.routes.Routes.EndPoint.userLoc
import com.example.scanmate.data.routes.Routes.EndPoint.userMenu
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

    @Multipart
    @POST(userMenu)
    suspend fun userMenu(
        @Part("UserNo") UserNo:RequestBody,
        @Part("LocationNo") LocationNo:RequestBody
    ):List<UserMenuResponse>

}