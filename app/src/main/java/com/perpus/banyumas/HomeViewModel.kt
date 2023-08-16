package com.perpus.banyumas

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.perpus.banyumas.data.response.DataAllBook
import com.perpus.banyumas.data.response.GetAllBookResponse
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
    private val _buku = MutableLiveData<GetAllBookResponse?>()
    val buku: LiveData<GetAllBookResponse?> get() = _buku

    fun getAllBook() {
        client.getAllBook()
            .enqueue(object : Callback<GetAllBookResponse> {
                override fun onResponse(
                    call: Call<GetAllBookResponse>,
                    response: Response<GetAllBookResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _buku.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<GetAllBookResponse>, t: Throwable) {
                }
            })
    }
}