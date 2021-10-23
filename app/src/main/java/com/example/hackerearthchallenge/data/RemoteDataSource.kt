package com.example.hackerearthchallenge.data


import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class RemoteDataSource @Inject constructor(){

    companion object {
        private const val BASE_URL = "https://api.stackexchange.com/2.2/"
        private const val apikey = "ZiXCZbWaOwnDgpVT9Hx8IA(("
    }

    fun <Api> buildApi(api: Class<Api>): Api {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val clientInterceptor = Interceptor { chain ->
            var request: Request = chain.request()
            val url: HttpUrl =
                request.url.newBuilder()
                    .addQueryParameter("key", apikey)
                    .addQueryParameter("order", "desc")
                    .addQueryParameter("sort", "activity")
                    .addQueryParameter("site", "stackoverflow")
                    .build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }


        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addNetworkInterceptor(clientInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }
}