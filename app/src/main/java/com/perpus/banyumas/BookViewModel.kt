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

    fun postPinjam(idpinjam: String, tglpinjam: String, idanggota: String, idpetugas: String) {
        client.postPinjam(PinjamRequest(idanggota, idpetugas, idpinjam, tglpinjam))
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

    val detailResult: MutableLiveData<BaseResponse<DetailPinjamResponse>> = MutableLiveData()
    fun postDetailPinjam(idbuku: String, idpinjam: String, jmlBuku: String) {
        client.postDetailPinjam(DetailPinjamRequest(idbuku, idpinjam, jmlBuku))
            .enqueue(object : Callback<DetailPinjamResponse> {
                override fun onResponse(
                    call: Call<DetailPinjamResponse>,
                    response: Response<DetailPinjamResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            detailResult.value = BaseResponse.Success(responseBody)
                        }
                    } else {
                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            val errorResponse = Gson().fromJson(errorBody.charStream(), ErrorResponse::class.java)
                            val errorCode = errorResponse.code
                            val errorMessage = errorResponse.message
                            detailResult.value = BaseResponse.Error(errorMessage)
                        } else {
                            detailResult.value = BaseResponse.Error("Unknown error occurred")
                        }
                    }
                }

                override fun onFailure(call: Call<DetailPinjamResponse>, t: Throwable) {
                }
            })
        detailResult
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