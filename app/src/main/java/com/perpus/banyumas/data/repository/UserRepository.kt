package com.perpus.banyumas.data.repository

import com.perpus.banyumas.data.request.LoginRequest
import com.perpus.banyumas.data.service.ApiHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun loginUser(loginRequest: LoginRequest) =
        apiHelper.loginUser(loginRequest = loginRequest)

}