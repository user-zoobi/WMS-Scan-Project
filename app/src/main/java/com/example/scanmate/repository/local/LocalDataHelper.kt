package com.example.scanmate.repository.local

import com.example.scanmate.data.response.GetWarehouseResponse
import com.example.scanmate.data.response.LoginResponse
import com.example.scanmate.data.response.UserLocationResponse
import com.example.scanmate.roomdb.LocalDatabase

class LocalDataHelper(
    private val db: LocalDatabase
    ): LocalRepository {

    override suspend fun insertUser(
        login: LoginResponse
    ) = db.appDao().insertData(login)

    override suspend fun insertWarehouse(
        getWarehouseResponse: GetWarehouseResponse
    ) = db.appDao().insertWarehouse(getWarehouseResponse)

    override suspend fun insertBusinessLocation(
        userLocation: UserLocationResponse
    ) = db.appDao().insertBusinessLoc(userLocation)

    override suspend fun getUser(
        login: LoginResponse
    ) = db.appDao().getUser(login)

}