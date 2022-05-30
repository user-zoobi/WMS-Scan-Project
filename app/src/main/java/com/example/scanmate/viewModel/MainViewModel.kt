package com.example.scanmate.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scanmate.data.callback.ApiResponseCallback
import com.example.scanmate.data.response.*
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


    private val _userMenu = MutableLiveData<ApiResponseCallback<List<UserMenuResponse>>>()
    val userMenu : LiveData<ApiResponseCallback<List<UserMenuResponse>>>
    get() = _userMenu

    fun userMenu(UserNo: RequestBody, LocationNo: RequestBody){
        _userMenu.value = ApiResponseCallback.loading()
        viewModelScope.launch {
            try {
                _userMenu.value = ApiResponseCallback.success(
                    repository.userMenu(UserNo, LocationNo)
                )
                Log.i(vmSuccess, responseFound)
            }catch (e: Exception){
                _userMenu.value = ApiResponseCallback.error("${e.message}", null)
                Log.i(vmError, "${e.message}")
            }
        }
    }


    private val _getWarehouse = MutableLiveData<ApiResponseCallback<List<GetWarehouseResponse>>>()
    val getWarehouse : LiveData<ApiResponseCallback<List<GetWarehouseResponse>>>
    get() = _getWarehouse
    fun getWarehouse(
        WH_Name: String, LocationNo: String
    ){
        _getWarehouse.value = ApiResponseCallback.loading()
        viewModelScope.launch {
            try {
                _getWarehouse.value = ApiResponseCallback.success(repository.getWarehouse(
                    WH_Name, LocationNo
                ))
            }catch (e:Exception){
                _getWarehouse.value = ApiResponseCallback.error("${e.message}",null)
            }
        }
    }


    private val _addUpdateWarehouse = MutableLiveData<ApiResponseCallback<AddUpdateWarehouseResponse>>()
    val addUpdateWarehouse : LiveData<ApiResponseCallback<AddUpdateWarehouseResponse>>
    get() = _addUpdateWarehouse
    fun addUpdateWarehouse(
        WH_No: String, WH_Name: String, WH_Code: String,
        LocationNo: String, DMLUserNo: String, DMLPCName: String
    ){
        viewModelScope.launch {
            _addUpdateWarehouse.value = ApiResponseCallback.loading()
            try {
                _addUpdateWarehouse.value = ApiResponseCallback.success(
                    repository.addUpdateWarehouse(
                        WH_No, WH_Name, WH_Code, LocationNo, DMLUserNo, DMLPCName
                    ))
            }catch (e:Exception){
                _addUpdateWarehouse.value = ApiResponseCallback.error(
                    "${e.message}",null
                )
            }
        }
    }


    private val _getRack = MutableLiveData<ApiResponseCallback<List<GetRackResponse>>>()
    val getRack : LiveData<ApiResponseCallback<List<GetRackResponse>>>
    get() = _getRack

    fun getRack(
        RackName: RequestBody, WH_No: RequestBody, LocationNo: RequestBody
    ){
        _getRack.value = ApiResponseCallback.loading()

        viewModelScope.launch {
            try {
                _getRack.value = ApiResponseCallback.success(repository.getRack(
                    RackName, WH_No, LocationNo
                ))
            }catch (e:Exception){
                _getRack.value = ApiResponseCallback.error("${e.message}",null)
            }
        }
    }


    private val _getShelf = MutableLiveData<ApiResponseCallback<List<GetShelfResponse>>>()
    val getShelf : LiveData<ApiResponseCallback<List<GetShelfResponse>>>
    get() = _getShelf

    fun getShelf(
        ShelfName: RequestBody, RackNo: RequestBody, LocationNo: RequestBody
    ){
        viewModelScope.launch {
            _getShelf.value = ApiResponseCallback.loading()

            try {
                _getShelf.value = ApiResponseCallback.success(repository.getShelf(
                    ShelfName, RackNo, LocationNo
                ))
            }catch (e:Exception){
                _getShelf.value = ApiResponseCallback.error("${e.message}",null)
            }
        }
    }


    private val _addRack = MutableLiveData<ApiResponseCallback<AddRackResponse>>()
    val addRack : LiveData<ApiResponseCallback<AddRackResponse>>
        get() = _addRack

    fun addRack(
        RackNo: RequestBody, RackName: RequestBody, RackCode: RequestBody, WH_No: RequestBody,
        Capacity: RequestBody, LocationNo: RequestBody, DMLUserNo: RequestBody, DMLPCName: RequestBody
    ){
        viewModelScope.launch {
            _addRack.value = ApiResponseCallback.loading()
            try {
                _addRack.value = ApiResponseCallback.success(repository.addRacks(
                    RackNo, RackName, RackCode, WH_No, Capacity, LocationNo, DMLUserNo, DMLPCName
                ))
            }catch (e:Exception){
                _addRack.value = ApiResponseCallback.error("${e.message}",null)
            }
        }
    }


    private val _addShelf = MutableLiveData<ApiResponseCallback<AddShelfResponse>>()
    val addShelf : LiveData<ApiResponseCallback<AddShelfResponse>>
        get() = _addShelf

    fun addShelf(
        RackNo: RequestBody, RackName: RequestBody, RackCode: RequestBody, WH_No: RequestBody,
        Capacity: RequestBody, LocationNo: RequestBody, DMLUserNo: RequestBody, DMLPCName: RequestBody
    ){
        viewModelScope.launch {
            _addShelf.value = ApiResponseCallback.loading()
            try {
                _addShelf.value = ApiResponseCallback.success(
                    repository.addShelf(
                        RackNo, RackName, RackCode, WH_No, Capacity, LocationNo, DMLUserNo, DMLPCName
                    ))
            }catch (e:Exception){
                _addShelf.value = ApiResponseCallback.error("${e.message}",null)
            }
        }
    }

}