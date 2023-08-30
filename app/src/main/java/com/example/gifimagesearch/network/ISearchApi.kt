package com.example.gifimagesearch.network

import com.example.gifimagesearch.model.ImageDataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ISearchApi {

    @GET("search")
    suspend fun searchImages(@Query("q") query: String,
                             @Query("api_key") apiKey: String,
                             @Query("limit") limit: Int,
                             @Query("offset") offset: Int) : Response<ImageDataResponse>
}