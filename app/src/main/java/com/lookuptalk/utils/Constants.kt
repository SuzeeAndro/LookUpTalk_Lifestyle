package com.lookuptalk.utils


import com.lookuptalk.retrofitservice.RetrofitService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object Constants {


    val LUT_Url = "http://15.207.44.182:3000/"
    val GOOGLE_BOOKS_Url = "https://www.googleapis.com/books/"
    val IMDB_Url = "https://api.themoviedb.org/"
    val API_Key = "6b869eb417d00f9ae626051993311996"
    val ImagePath = "https://image.tmdb.org/t/p/w500"
    val SPOTIFY_URL = "https://api.spotify.com/"

    /*Spotify*/
    val CLIENT_ID = "aaa4ed71deb0435abbdbb7f9446184c2"
    val AUTH_TOKEN_REQUEST_CODE = 0x10
    val REDIRECT_URI = "com.lookuptalk://callback"



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

    val GoogleBooks_service = Retrofit.Builder()
        .baseUrl(GOOGLE_BOOKS_Url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create<RetrofitService>(RetrofitService::class.java!!)

    val spotify_service = Retrofit.Builder()
        .baseUrl(SPOTIFY_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create<RetrofitService>(RetrofitService::class.java!!)


}