package com.example.scanmate.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scanmate.storage.data.callback.ApiResponseCallback
import com.example.scanmate.storage.data.response.LoginResponse
import com.example.scanmate.storage.data.response.UserLocationResponse
import com.example.scanmate.repository.GeneralRepository
import com.example.scanmate.util.Constants.LogMessages.responseFound
import com.example.scanmate.util.Constants.Logs.vmError
import com.example.scanmate.util.Constants.Logs.vmSuccess
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import java.lang.Exception

class MainViewModel : ViewModel() {

    private val repository = GeneralRepository()
    private val _data = MutableLiveData<ApiResponseCallback<List<LoginResponse>>>()
    val data: LiveData<ApiResponseCallback<List<LoginResponse>>>
    get() = _data

    fun loginUser(UserID: RequestBody, Pwd: RequestBody) {
        viewModelScope.launch {
            _data.value = ApiResponseCallback.loading()
            try {
                _data.value = ApiResponseCallback.success(
                    repository.userAuthLogin(
                        UserID, Pwd
                    )
                )
                Log.i(vmSuccess, responseFound)
            } catch (e: Exception) {
                _data.value = ApiResponseCallback.error("${e.message}", null)
                Log.i(vmError, "${e.message}")
            }
        }
    }


    private val _userLoc = MutableLiveData<ApiResponseCallback<List<UserLocationResponse>>>()
    val userLoc : LiveData<ApiResponseCallback<List<UserLocationResponse>>>
    get() = _userLoc

    fun userLocation(UserNo:RequestBody){
        _userLoc.value = ApiResponseCallback.loading()
        viewModelScope.launch {
            try {
                _userLoc.value = ApiResponseCallback.success(
                    repository.userLocation(UserNo)
                )
                Log.i(vmSuccess, responseFound)
            }catch (e: Exception) {
                _userLoc.value = ApiResponseCallback.error("${e.message}", null)
                Log.i(vmError, "${e.message}")
            }
        }
    }

}