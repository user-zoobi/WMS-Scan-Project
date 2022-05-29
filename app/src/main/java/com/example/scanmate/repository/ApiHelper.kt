package com.example.scanmate.repository

import com.example.scanmate.data.api.RetrofitClient
import com.example.scanmate.data.response.*
import okhttp3.RequestBody
import retrofit2.http.Field

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

    suspend fun getWarehouse(
        WH_Name:String,
        LocationNo:String
    ):List<GetWarehouseResponse> = RetrofitClient.apiservice.getWarehouse(WH_Name, LocationNo)

    suspend fun addUpdateWarehouse(
        WH_No:String, WH_Name:String, WH_Code:String, LocationNo:String, DMLUserNo:String, DMLPCName:String,
    ):AddUpdateWarehouseResponse
    = RetrofitClient.apiservice.addUpdateWarehouse(
        WH_No, WH_Name, WH_Code, LocationNo, DMLUserNo, DMLPCName
    )
}