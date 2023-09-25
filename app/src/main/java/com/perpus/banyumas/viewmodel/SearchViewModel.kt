package com.perpus.banyumas.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.perpus.banyumas.data.response.BaseResponse
import com.perpus.banyumas.data.response.ErrorResponse
import com.perpus.banyumas.data.response.SearchBookResponse
import com.perpus.banyumas.data.service.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val client: ApiService
) : ViewModel() {

    val searchBookResult: MutableLiveData<BaseResponse<SearchBookResponse>> = MutableLiveData()

    fun searchBook(keyword: String) {
        searchBookResult.value = BaseResponse.Loading()

        client.searchBook(keyword)
            .enqueue(object : Callback<SearchBookResponse> {
                override fun onResponse(
                    call: Call<SearchBookResponse>,
                    response: Response<SearchBookResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        searchBookResult.value = BaseResponse.Success(responseBody)
                    } else {
                        val errorBody = response.errorBody()
                        if (errorBody != null) {
                            val errorResponse =
                                Gson().fromJson(errorBody.charStream(), ErrorResponse::class.java)
                            val errorMessage = errorResponse.message
                            searchBookResult.value = BaseResponse.Error(errorMessage)
                        } else {
                            searchBookResult.value = BaseResponse.Error("Unknown error occurred")
                        }
                    }
                }

                override fun onFailure(call: Call<SearchBookResponse>, t: Throwable) {
                    searchBookResult.value = BaseResponse.Error("Network Error")
                }
            })
        searchBookResult

    }
}
