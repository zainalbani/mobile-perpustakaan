package com.perpus.banyumas

import android.app.Application
import androidx.lifecycle.*
import com.google.gson.Gson
import com.perpus.banyumas.data.request.DetailPinjamRequest
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
                }
            })
        pinjamResult
    }

    private val _pinjamid = MutableLiveData<GetPinjamByIdResponse?>()
    val pinjamid: LiveData<GetPinjamByIdResponse?> get() = _pinjamid


    fun getPinjamById(idanggota: String) {
        client.getPinjamById(idanggota)
            .enqueue(object : Callback<GetPinjamByIdResponse> {
                override fun onResponse(
                    call: Call<GetPinjamByIdResponse>,
                    response: Response<GetPinjamByIdResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _pinjamid.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<GetPinjamByIdResponse>, t: Throwable) {
                }
            })
    }

    private val _detpinjamid = MutableLiveData<GetDetailPinjamByIdResponse?>()
    val detpinjamid: LiveData<GetDetailPinjamByIdResponse?> get() = _detpinjamid
    fun getDetailPinjamById(idpinjam: String) {
        client.getDetailPinjamById(idpinjam)
            .enqueue(object : Callback<GetDetailPinjamByIdResponse> {
                override fun onResponse(
                    call: Call<GetDetailPinjamByIdResponse>,
                    response: Response<GetDetailPinjamByIdResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _detpinjamid.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<GetDetailPinjamByIdResponse>, t: Throwable) {
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