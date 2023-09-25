package com.perpus.banyumas.data.service

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

    @GET("/buku/getidpinjam")
    fun getIdPinjam() : Call<GetIdPinjam>

    @POST("/buku/peminjaman/{idanggota}")
    fun postPinjam(@Body request: PinjamRequest,@Path("idanggota") idanggota: String) : Call<PinjamResponse>

    @GET("/buku/getpinjambyid/{idanggota}")
    fun getPinjamById(@Path("idanggota") idanggota: String) : Call<GetPinjamByIdResponse>

    @GET("/buku/getdetpinjambyid/{idpinjam}")
    fun getDetailPinjamById(@Path("idpinjam") idpinjam: String) : Call<DataDetPinjamById>

    @GET("/buku/getbookbyid/{idbuku}")
    fun getBookById(@Path("idbuku") idbuku: String) : Call<GetBookByIdResponse>

    @GET("/buku/getdetailbuku/{idbuku}")
    fun getDetailBuku(@Path("idbuku") idbuku: String) : Call<GetDetailBuku>

    @GET("/search/book")
    fun searchBook(@Query("keyword") keyword:String) : Call<SearchBookResponse>
}