package com.perpus.banyumas.data.service

import com.perpus.banyumas.data.request.LoginRequest
import com.perpus.banyumas.data.response.AuthResponse
import retrofit2.Response

interface ApiHelper {

    suspend fun loginUser(loginRequest: LoginRequest): Response<AuthResponse>
}