package com.example.scanmate.repository.local

import androidx.room.RoomDatabase
import com.example.scanmate.data.response.GetWarehouseResponse
import com.example.scanmate.data.response.LoginResponse
import com.example.scanmate.data.response.UserLocationResponse
import com.example.scanmate.roomdb.LocalDatabase

interface LocalRepository{

    suspend fun insertUser(login:LoginResponse)
    suspend fun insertWarehouse(getWarehouseResponse: GetWarehouseResponse)
    suspend fun insertBusinessLocation(userLocation:UserLocationResponse)
    suspend fun getUser(login: LoginResponse)
}