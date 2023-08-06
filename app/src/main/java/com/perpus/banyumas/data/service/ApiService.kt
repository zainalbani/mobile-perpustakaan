package com.perpus.banyumas.data.service

import com.perpus.banyumas.data.request.DetailPinjamRequest
import com.perpus.banyumas.data.request.LoginRequest
import com.perpus.banyumas.data.request.PasswordRequest
import com.perpus.banyumas.data.request.PinjamRequest
import com.perpus.banyumas.data.response.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<AuthResponse>

    @GET("/getbyid/{idanggota}")
    fun getById(@Path("idanggota") idanggota: String) : Call<GetByIdResponse>

    @PUT("/update/{idanggota}")
    fun updatePassword(@Body passwordRequest: PasswordRequest, @Path("idanggota") idanggota: String): Call<PasswordResponse>

    @GET("/getallbook")
    fun getAllBook() : Call<GetAllBookResponse>

    @POST("/peminjaman")
    fun postPinjam(@Body request: PinjamRequest) : Call<PinjamResponse>

    @POST("/detailpinjam")
    fun postDetailPinjam(@Body request: DetailPinjamRequest) : Call<DetailPinjamResponse>



}