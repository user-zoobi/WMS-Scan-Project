package com.example.scanmate.storage.roomdb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_login")
data class UserAuth(

    @ColumnInfo(name ="UserNo")
    val UserNo: Int,

    @ColumnInfo(name ="UserID")
    val UserID: String,

    @ColumnInfo(name ="UserName")
    val UserName: String,

    @ColumnInfo(name ="User_CategoryNo")
    val User_CategoryNo: Int,

    @ColumnInfo(name ="User_CategoryName")
    val User_CategoryName: String,

    @ColumnInfo(name ="MobileNo")
    val MobileNo: String,

    @ColumnInfo(name ="ExtensionNo")
    val ExtensionNo: String,

    @ColumnInfo(name ="EmailID")
    val EmailID: String,

    @ColumnInfo(name ="Active")
    val Active: String,

    @ColumnInfo(name ="EmpNo")
    val EmpNo: Int,

    @ColumnInfo(name ="DeptNo")
    val DeptNo: Int,

    @ColumnInfo(name ="DeptName")
    val DeptName: String,

    @ColumnInfo(name ="Super")
    val Super: Boolean,

    @ColumnInfo(name ="Administrator")
    val Administrator: Boolean,

    @ColumnInfo(name ="Guest")
    val Guest: Boolean,

    @ColumnInfo(name ="PwdExpired")
    val PwdExpired: Boolean,

    @ColumnInfo(name ="Status")
    val Status: Boolean,

    @ColumnInfo(name ="Error")
    val Error: String,

){
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}