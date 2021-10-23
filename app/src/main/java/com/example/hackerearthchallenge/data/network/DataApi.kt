package com.example.hackerearthchallenge.data.network

import com.example.hackerearthchallenge.data.models.DataResponse
import retrofit2.http.GET


interface DataApi {

    @GET("questions")
    suspend fun getQuestionData(): DataResponse

}