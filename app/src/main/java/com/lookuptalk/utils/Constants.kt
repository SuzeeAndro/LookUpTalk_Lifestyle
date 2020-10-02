package com.lb.utils


import android.graphics.Color
import com.lb.retrofitservice.RetrofitService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object Constants {


    val LUT_Url = "http://15.207.44.182:3000/"
    val IMDB_Url = "https://api.themoviedb.org/"
    val API_Key = "6b869eb417d00f9ae626051993311996"
    val ImagePath = "https://image.tmdb.org/t/p/w500"




    val okHttpClient = OkHttpClient.Builder()
        .readTimeout(80000, TimeUnit.SECONDS)
        .connectTimeout(80000, TimeUnit.SECONDS)
        .build()


    val retrofitService = Retrofit.Builder()
        .baseUrl(LUT_Url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create<RetrofitService>(RetrofitService::class.java!!)


 val Imdb_service = Retrofit.Builder()
        .baseUrl(IMDB_Url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create<RetrofitService>(RetrofitService::class.java!!)


}