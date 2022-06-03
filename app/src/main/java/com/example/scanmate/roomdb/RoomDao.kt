package com.example.scanmate.roomdb

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.scanmate.data.response.AddUpdateWarehouseResponse
import com.example.scanmate.data.response.GetWarehouseResponse
import com.example.scanmate.data.response.LoginResponse
import com.example.scanmate.data.response.UserLocationResponse

interface RoomDao {

    @Insert
    suspend fun insertData(loginResponse: LoginResponse)

    @Insert
    suspend fun insertWarehouse(getWarehouseResponse: GetWarehouseResponse)

    @Insert
    suspend fun insertBusinessLoc(userLocationResponse: UserLocationResponse)

    @Query("UPDATE users SET id=:id WHERE id=id")
    suspend fun updateWarehouse(id:Int)

    @Query("SELECT * FROM users")
    suspend fun getUser(loginResponse:LoginResponse)

    @Query("DELETE FROM users WHERE id=:id")
    suspend fun delete (id: Int)

}