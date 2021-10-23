package com.example.hackerearthchallenge.data.repository



import com.example.hackerearthchallenge.data.network.SafeApiCall
import com.example.hackerearthchallenge.data.network.DataApi
import javax.inject.Inject

class HomeRepository @Inject constructor(private val api: DataApi): SafeApiCall {

    suspend fun getQuesData() = safeApiCall {
        api.getQuestionData()
    }
}