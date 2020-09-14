package com.lb.utils


import android.graphics.Color
import com.lb.retrofitservice.RetrofitService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object Constants {


    val LUT_Url = "http://15.207.44.182:3000/"

    val PIE_CHART_COLORS = intArrayOf(
        Color.rgb(36, 247, 26),
        Color.rgb(227, 7, 30),
        Color.rgb(227, 7, 30)
    )

    val PREFS_NAME = "LOGIN DETAILS"


    val OTHER = "Other"
    val CALL_NOT_MADE = "Call Not Made"
    val CALL_MADE = "Call Made"
    val LOGINUSER_MOBILE = "login_mobile"



    val okHttpClient = OkHttpClient.Builder()
        .readTimeout(80000, TimeUnit.SECONDS)
        .connectTimeout(80000, TimeUnit.SECONDS)
        .build()


    val retrofitService = Retrofit.Builder()
        .baseUrl(LUT_Url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create<RetrofitService>(RetrofitService::class.java!!)


}