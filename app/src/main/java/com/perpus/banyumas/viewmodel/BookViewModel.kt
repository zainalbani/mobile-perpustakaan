package com.perpus.banyumas.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.google.gson.Gson
import com.perpus.banyumas.data.request.PinjamRequest
import com.perpus.banyumas.data.response.*
import com.perpus.banyumas.data.service.ApiService
import com.perpus.banyumas.utils.UserDataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val client: ApiService,
    private val pref: UserDataStoreManager,
    application: Application
) : AndroidViewModel(application) {

    val pinjamResult: MutableLiveData<BaseResponse<PinjamResponse>> = MutableLiveData()

    fun postPinjam(idpinjam: String, idbuku: String, idanggota: String) {
        pinjamResult.value = BaseResponse.Loading()
        client.postPinjam(PinjamRequest(idpinjam, idbuku), idanggota)
            .enqueue(object : Callback<PinjamResponse> {
                override fun onResponse(
                    call: Call<PinjamResponse>,
                    response: Response<PinjamResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            pinjamResult.value = BaseResponse.Success(responseBody)
                        }
                    } else {
                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            val errorResponse = Gson().fromJson(errorBody.charStream(), ErrorResponse::class.java)
                            val errorCode = errorResponse.code
                            val errorMessage = errorResponse.message
                            pinjamResult.value = BaseResponse.Error(errorMessage)
                        } else {
                            pinjamResult.value = BaseResponse.Error("Unknown error occurred")
                        }
                    }
                }

                override fun onFailure(call: Call<PinjamResponse>, t: Throwable) {
                    pinjamResult.value = BaseResponse.Error("Network Error")
                }
            })
        pinjamResult
    }

    val pinjamid: MutableLiveData<BaseResponse<GetPinjamByIdResponse>> = MutableLiveData()


    fun getPinjamById(idanggota: String) {
        pinjamid.value = BaseResponse.Loading()
        client.getPinjamById(idanggota)
            .enqueue(object : Callback<GetPinjamByIdResponse> {
                override fun onResponse(
                    call: Call<GetPinjamByIdResponse>,
                    response: Response<GetPinjamByIdResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            pinjamid.value = BaseResponse.Success(responseBody)
                        } else {
                            val errorBody = response.errorBody()
                            if (errorBody != null) {
                                val errorResponse =
                                    Gson().fromJson(errorBody.charStream(), ErrorResponse::class.java)
                                val errorMessage = errorResponse.message
                                pinjamid.value = BaseResponse.Error(errorMessage)
                            } else {
                                pinjamid.value = BaseResponse.Error("Unknown error occurred")
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<GetPinjamByIdResponse>, t: Throwable) {
                    pinjamid.value = BaseResponse.Error("Network Error")
                }
            })
        pinjamid
    }

    private val _detpinjamid = MutableLiveData<DataDetPinjamById?>()
    val detpinjamid: LiveData<DataDetPinjamById?> get() = _detpinjamid
    fun getDetailPinjamById(idpinjam: String) {
        client.getDetailPinjamById(idpinjam)
            .enqueue(object : Callback<DataDetPinjamById> {
                override fun onResponse(
                    call: Call<DataDetPinjamById>,
                    response: Response<DataDetPinjamById>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _detpinjamid.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<DataDetPinjamById>, t: Throwable) {
                }
            })
    }
    private val _getpinjam = MutableLiveData<GetIdPinjam?>()
    val getpinjam: LiveData<GetIdPinjam?> get() = _getpinjam


    fun getIdPinjam() {
        client.getIdPinjam()
            .enqueue(object : Callback<GetIdPinjam> {
                override fun onResponse(
                    call: Call<GetIdPinjam>,
                    response: Response<GetIdPinjam>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _getpinjam.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<GetIdPinjam>, t: Throwable) {
                }
            })
    }
    private val _getbuku = MutableLiveData<GetBookByIdResponse?>()
    val getbuku: LiveData<GetBookByIdResponse?> get() = _getbuku

    fun getBookById(idbuku: String) {
        client.getBookById(idbuku)
            .enqueue(object : Callback<GetBookByIdResponse> {
                override fun onResponse(
                    call: Call<GetBookByIdResponse>,
                    response: Response<GetBookByIdResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _getbuku.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<GetBookByIdResponse>, t: Throwable) {
                }
            })
    }

    private val _detailbuku = MutableLiveData<GetDetailBuku?>()
    val detailbuku: LiveData<GetDetailBuku?> get() = _detailbuku

    fun getDetailBuku(idbuku: String) {
        client.getDetailBuku(idbuku)
            .enqueue(object : Callback<GetDetailBuku> {
                override fun onResponse(
                    call: Call<GetDetailBuku>,
                    response: Response<GetDetailBuku>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _detailbuku.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<GetDetailBuku>, t: Throwable) {
                }
            })
    }


    fun saveIdBook(idbuku: String) {
        viewModelScope.launch {
            pref.saveIdBook(idbuku)
        }
    }
    fun getIdBook(): LiveData<String> {
        return pref.getIdBook.asLiveData()
    }


    fun removeIdBook() {
        viewModelScope.launch {
            pref.removeIdBook()
        }
    }

}