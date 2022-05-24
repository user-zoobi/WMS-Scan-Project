package com.example.scanmate.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse {

    @SerializedName("UserNo")
    @Expose
    private val userNo: Int? = null

    @SerializedName("UserID")
    @Expose
    private val userID: String? = null

    @SerializedName("UserName")
    @Expose
    private val userName: String? = null

    @SerializedName("User_CategoryNo")
    @Expose
    private val userCategoryNo: Int? = null

    @SerializedName("User_CategoryName")
    @Expose
    private val userCategoryName: String? = null

    @SerializedName("MobileNo")
    @Expose
    private val mobileNo: String? = null

    @SerializedName("ExtensionNo")
    @Expose
    private val extensionNo: String? = null

    @SerializedName("EmailID")
    @Expose
    private val emailID: String? = null

    @SerializedName("Active")
    @Expose
    private val active: Boolean? = null

    @SerializedName("EmpNo")
    @Expose
    private val empNo: Int? = null

    @SerializedName("DeptNo")
    @Expose
    private val deptNo: Int? = null

    @SerializedName("DeptName")
    @Expose
    private val deptName: String? = null

    @SerializedName("Super")
    @Expose
    private val _super: Boolean? = null

    @SerializedName("Administrator")
    @Expose
    private val administrator: Boolean? = null

    @SerializedName("Guest")
    @Expose
    private val guest: Boolean? = null

    @SerializedName("PwdExpired")
    @Expose
    private val pwdExpired: Boolean? = null

    @SerializedName("Status")
    @Expose
    private val status: Boolean? = null

    @SerializedName("Error")
    @Expose
    private val error: String? = null

}