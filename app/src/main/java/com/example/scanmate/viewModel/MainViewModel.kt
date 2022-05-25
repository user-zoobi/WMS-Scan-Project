package com.example.scanmate.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scanmate.data.callback.ApiResponseCallback
import com.example.scanmate.data.response.LoginResponse
import com.example.scanmate.repository.GeneralRepository
import com.example.scanmate.util.Constants.LogMessages.responseFailed
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

}