package com.example.retrofittest.model.remote

import com.example.retrofittest.model.remote.datamodel.ReceiveDataModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("everything?apiKey=f77e7668c7db481fa9b7b9dff9d5bd60")
    suspend fun getAll(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ):ReceiveDataModel
}