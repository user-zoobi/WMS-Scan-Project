package com.example.scanmate.storage.roomdb.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.scanmate.data.response.LoginResponse

@Dao
interface UserDao{

    @Insert
    fun insert(loginResponse: LoginResponse)

    @Query("select * from user_login")
    fun getAllUsers(): LiveData<List<LoginResponse>>

}
