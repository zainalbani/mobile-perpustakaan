package com.perpus.banyumas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.perpus.banyumas.data.response.BaseResponse
import com.perpus.banyumas.data.response.DataAllBook
import com.perpus.banyumas.data.response.ErrorResponse
import com.perpus.banyumas.data.response.GetAllBookResponse
import com.perpus.banyumas.data.response.PinjamResponse
import com.perpus.banyumas.data.service.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val client: ApiService,
    application: Application
) : AndroidViewModel(application) {

    private val list = ArrayList<DataAllBook>()
    val bukuResult: MutableLiveData<BaseResponse<GetAllBookResponse>> = MutableLiveData()

    fun getAllBook() {
        bukuResult.value = BaseResponse.Loading()
        client.getAllBook()
            .enqueue(object : Callback<GetAllBookResponse> {
                override fun onResponse(
                    call: Call<GetAllBookResponse>,
                    response: Response<GetAllBookResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            bukuResult.value = BaseResponse.Success(responseBody)
                        }
                    } else {
                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            val errorResponse = Gson().fromJson(errorBody.charStream(), ErrorResponse::class.java)
                            val errorCode = errorResponse.code
                            val errorMessage = errorResponse.message
                            bukuResult.value = BaseResponse.Error(errorMessage)
                        } else {
                            bukuResult.value = BaseResponse.Error("Unknown error occurred")
                        }
                    }
                }

                override fun onFailure(call: Call<GetAllBookResponse>, t: Throwable) {
                    bukuResult.value = BaseResponse.Error("Network Error")
                }
            })
        bukuResult
    }
}