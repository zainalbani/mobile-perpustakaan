package com.perpus.banyumas.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.google.gson.Gson
import com.perpus.banyumas.data.repository.UserRepository
import com.perpus.banyumas.data.request.LoginRequest
import com.perpus.banyumas.data.response.AuthResponse
import com.perpus.banyumas.data.response.BaseResponse
import com.perpus.banyumas.data.response.ErrorResponse
import com.perpus.banyumas.utils.UserDataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val pref: UserDataStoreManager,
    application: Application
) : AndroidViewModel(application) {

    val loginResult: MutableLiveData<BaseResponse<AuthResponse>> = MutableLiveData()

    fun loginUser(email: String, pwd: String) {

        loginResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(
                    pass = pwd,
                    email = email
                )
                val response = userRepo.loginUser(loginRequest = loginRequest)
                if (response?.code() == 200) {
                    loginResult.value = BaseResponse.Success(response.body())
                } else {
                    val errorBody = response.errorBody()
                    if (errorBody != null) {
                        val errorResponse = Gson().fromJson(errorBody.charStream(), ErrorResponse::class.java)
                        val errorCode = errorResponse.code
                        val errorMessage = errorResponse.message
                        loginResult.value = BaseResponse.Error(errorMessage)
                    } else {
                        loginResult.value = BaseResponse.Error("Unknown error occurred")
                    }
                }

            } catch (ex: Exception) {
                loginResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
    fun saveIsLoginStatus(status: Boolean) {
        viewModelScope.launch {
            pref.saveIsLoginStatus(status)
        }
    }

    fun getDataStoreIsLogin(): LiveData<Boolean> {
        return pref.getIsLogin.asLiveData()
    }

    fun getDataStoreUsername(): LiveData<String> {
        return pref.getUsername.asLiveData()
    }

    fun saveUsername(username: String) {
        viewModelScope.launch {
            pref.saveUsername(username)
        }
    }


    fun saveId(idanggota: String) {
        viewModelScope.launch {
            pref.saveId(idanggota)
        }
    }
}