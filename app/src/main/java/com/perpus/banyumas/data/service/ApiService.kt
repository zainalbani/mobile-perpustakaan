package com.perpus.banyumas.data.service

import com.perpus.banyumas.data.request.LoginRequest
import com.perpus.banyumas.data.response.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<AuthResponse>
}