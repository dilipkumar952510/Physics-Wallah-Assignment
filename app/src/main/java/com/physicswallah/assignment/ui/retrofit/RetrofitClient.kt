package com.physicswallah.assignment.ui.retrofit

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.TimeZone
import java.util.concurrent.TimeUnit

class RetrofitClient {
    private var authToken : String?=null

    companion object {
    const val BASE_URL = "https://rickandmortyapi.com/api/"
    }

    fun <Api> buildApi(
        api : Class<Api>,
        authToken : String? = null
    ): Api {
        this.authToken = authToken
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setLenient().create()))
            .client(okHttpClient).build()
            .create(api)
    }

    private val okHttpClient: OkHttpClient
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            return OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(Interceptor { chain ->
                    val request = chain.request().newBuilder()
                    request.method(chain.request().method, chain.request().body)
                    request.addHeader("accept", "application/json")
                    request.addHeader("Content-Type","application/json")
                    request.addHeader("platform","1")
//                    request.addHeader("timezone","0")
                    request.addHeader("timezone", TimeZone.getDefault().id)
                    request.addHeader("language","en")
                    request.addHeader("offset",TimeZone.getDefault().rawOffset.toString())
                    request.addHeader("offset","0")
                    if(authToken !=null)
                        request.addHeader("Authorization",authToken!!)
                    request.build()
                    chain.proceed(request.build())
                })
                .readTimeout(60, TimeUnit.SECONDS)
                .build()
        }
}