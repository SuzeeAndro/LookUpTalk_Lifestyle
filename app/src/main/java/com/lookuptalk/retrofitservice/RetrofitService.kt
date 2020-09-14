package com.lb.retrofitservice

import com.google.gson.JsonObject
import com.lookuptalk.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by SUJITH on 19/10/2019
 */
interface RetrofitService {

    @POST("/api/user/authenticate/login")
    fun getlogin(@Body body: JsonObject): Call<LoginResponse>

    /*Get Flags Data*/
    @get:GET("/api/lookuptalk/flags/offset/0")
    val flagsdata: Call<ResponseBody>

    /*Personal Data*/
    @POST("/api/social/profile")
    fun updatePersonal(@Body body: JsonObject): Call<LoginResponse>


    /*Occupations Data*/
    @get:GET("/api/master/occupations")
    val getOccupations: Call<ResponseBody>

    /*Interest Data*/
    @get:GET("/api/master/lifestyle/interests")
    val getHobiesInterest: Call<ResponseBody>

    /*SPorts Data*/
    @get:GET("/api/master/lifestyle/sports")
    val getSports: Call<ResponseBody>

    /*countries Data*/
    @get:GET("/api/master/lifestyle/countries")
    val getcountries: Call<ResponseBody>

    /*Fashion Data*/
    @get:GET("/api/master/lifestyle/fashions")
    val getfashions: Call<ResponseBody>

    /*Cusines Data*/
    @get:GET("/api/master/lifestyle/cuisines")
    val getcuisines: Call<ResponseBody>

    /*Pets*/
    @get:GET("api/master/lifestyle/pets")
    val getpets: Call<ResponseBody>


    /*Music*/
    @get:GET("api/master/lifestyle/musics")
    val getMusic: Call<ResponseBody>

    /*Shows*/
    @get:GET("api/master/lifestyle/shows")
    val getShows: Call<ResponseBody>


    /*books*/
    @get:GET("api/master/lifestyle/books")
    val getBooks: Call<ResponseBody>

    /*movies*/
    @get:GET("api/master/lifestyle/movies")
    val getMovies: Call<ResponseBody>

    /*Occupations Data*/
    @get:GET("/api/master/heights")
    val getHeights: Call<ResponseBody> /*Occupations Data*/


    /*Industry*/
    @get:GET("/api/master/industrys")
    val getIndustry: Call<ResponseBody>

    /*Designation*/
    @get:GET("/api/master/designations")
    val getDesignations: Call<ResponseBody>

    /*Skills*/
    @get:GET("/api/master/skills")
    val getskills: Call<ResponseBody>


}
