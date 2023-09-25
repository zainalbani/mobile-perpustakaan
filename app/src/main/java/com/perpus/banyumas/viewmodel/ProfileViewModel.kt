package com.perpus.banyumas.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.google.gson.Gson
import com.perpus.banyumas.data.request.PasswordRequest
import com.perpus.banyumas.data.response.BaseResponse
import com.perpus.banyumas.data.response.ErrorResponse
import com.perpus.banyumas.data.response.GetByIdResponse
import com.perpus.banyumas.data.response.PasswordResponse
import com.perpus.banyumas.data.response.SearchBookResponse
import com.perpus.banyumas.data.service.ApiService
import com.perpus.banyumas.utils.UserDataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val client: ApiService,
    private val pref: UserDataStoreManager,
    application: Application
) : AndroidViewModel(application) {

    val userResult: MutableLiveData<BaseResponse<GetByIdResponse>> = MutableLiveData()

    fun getUserProfile(idanggota: String) {
        userResult.value = BaseResponse.Loading()
        client.getById(idanggota)
            .enqueue(object : Callback<GetByIdResponse> {
                override fun onResponse(
                    call: Call<GetByIdResponse>,
                    response: Response<GetByIdResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        userResult.value = BaseResponse.Success(responseBody)
                    } else {
                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            val errorResponse =
                                Gson().fromJson(errorBody.charStream(), ErrorResponse::class.java)
                            val errorMessage = errorResponse.message
                            userResult.value = BaseResponse.Error(errorMessage)
                        } else {
                            userResult.value = BaseResponse.Error("Unknown error occurred")
                        }
                    }
                }

                override fun onFailure(call: Call<GetByIdResponse>, t: Throwable) {
                    userResult.value = BaseResponse.Error("Network Error")
                }
            })
        userResult
    }

    val updateResult: MutableLiveData<BaseResponse<PasswordResponse>> = MutableLiveData()

    fun updatePassword(
        oldPassword: String,
        newPassword: String,
        confirmNewPassword: String,
        idanggota: String
    ) {
        updateResult.value = BaseResponse.Loading()
        client.updatePassword(
            PasswordRequest(oldPassword, newPassword, confirmNewPassword),
            idanggota
        )
            .enqueue(object : Callback<PasswordResponse> {
                override fun onResponse(
                    call: Call<PasswordResponse>,
                    response: Response<PasswordResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            updateResult.value = BaseResponse.Success(responseBody)
                        }
                    } else {
                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            val errorResponse =
                                Gson().fromJson(errorBody.charStream(), ErrorResponse::class.java)
                            val errorCode = errorResponse.code
                            val errorMessage = errorResponse.message
                            updateResult.value = BaseResponse.Error(errorMessage)
                        } else {
                            updateResult.value = BaseResponse.Error("Unknown error occurred")
                        }
                    }
                }

                override fun onFailure(call: Call<PasswordResponse>, t: Throwable) {
                    updateResult.value = BaseResponse.Error("Network Error")
                }
            })
        updateResult
    }

    fun getId(): LiveData<String> {
        return pref.getId.asLiveData()
    }

    fun removeIsLoginStatus() {
        viewModelScope.launch {
            pref.removeIsLoginStatus()
        }
    }

    fun removeUsername() {
        viewModelScope.launch {
            pref.removeUsername()
        }
    }

    fun removeId() {
        viewModelScope.launch {
            pref.removeId()
        }
    }

    fun getDataStoreIsLogin(): LiveData<Boolean> {
        return pref.getIsLogin.asLiveData()
    }
}