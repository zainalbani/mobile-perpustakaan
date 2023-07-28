package com.perpus.banyumas.data.service

import com.perpus.banyumas.data.request.LoginRequest
import com.perpus.banyumas.data.response.AuthResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService): ApiHelper {

    override suspend fun loginUser(loginRequest: LoginRequest): Response<AuthResponse> =
        apiService.loginUser(loginRequest = loginRequest)
}