package com.lookuptalk.retrofitservice

import android.content.Context
import com.lookuptalk.utils.Constants
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServiceBuilder(val context: Context) {


    private var retrofitApi: Retrofit? = null
    private val interceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }
    fun getRetrofit(): Retrofit? {
        if (retrofitApi == null) {
            val builder = OkHttpClient.Builder()
            val okHttpClient = builder.addInterceptor(AuthenticationInterceptor(context)).addNetworkInterceptor(interceptor) .connectTimeout(5,TimeUnit.MINUTES)
                .readTimeout(5,TimeUnit.MINUTES).writeTimeout(5,TimeUnit.MINUTES).connectionPool(
                    ConnectionPool(0,1,TimeUnit.SECONDS)
                ).retryOnConnectionFailure(true).build()

            val BASE_URL = Constants.LUT_Url
            retrofitApi = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }
        return retrofitApi
    }

    }


