package com.example.scanmate.util

object Constants {

    const val BASE_URL = "https://call.i-konnect.net/"


    object EndPoint{
        const val userAuth = "GAuthAPI/Get_SM_User_Auth"
        const val userLoc = "WHUserAPI/Get_SM_User_Location"
    }

    object Logs{
        const val vmSuccess = "ViewModelSuccess"
        const val vmLoading = "ViewModelLoading"
        const val vmError = "ViewModelError"
    }

    object LogMessages{
        const val responseFound = "Response Found"
        const val responseFailed = "Response Failed"
        const val loading = "Response Loading"
        const val success = "Observer Success"
        const val error = "Observer Error"
    }

}