package com.example.scanmate.storage.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse {

    @SerializedName("UserNo")
    @Expose
     val userNo: Int? = null

    @SerializedName("UserID")
    @Expose
     val userID: String? = null

    @SerializedName("UserName")
    @Expose
     val userName: String? = null

    @SerializedName("User_CategoryNo")
    @Expose
     val userCategoryNo: Int? = null

    @SerializedName("User_CategoryName")
    @Expose
     val userCategoryName: String? = null

    @SerializedName("MobileNo")
    @Expose
     val mobileNo: String? = null

    @SerializedName("ExtensionNo")
    @Expose
     val extensionNo: String? = null

    @SerializedName("EmailID")
    @Expose
     val emailID: String? = null

    @SerializedName("Active")
    @Expose
     val active: Boolean? = null

    @SerializedName("EmpNo")
    @Expose
     val empNo: Int? = null

    @SerializedName("DeptNo")
    @Expose
     val deptNo: Int? = null

    @SerializedName("DeptName")
    @Expose
     val deptName: String? = null

    @SerializedName("Super")
    @Expose
     val _super: Boolean? = null

    @SerializedName("Administrator")
    @Expose
     val administrator: Boolean? = null

    @SerializedName("Guest")
    @Expose
     val guest: Boolean? = null

    @SerializedName("PwdExpired")
    @Expose
     val pwdExpired: Boolean? = null

    @SerializedName("Status")
    @Expose
     val status: Boolean? = null

    @SerializedName("Error")
    @Expose
     val error: String? = null

}