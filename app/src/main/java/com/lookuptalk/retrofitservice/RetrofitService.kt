package com.lookuptalk.retrofitservice

import com.google.gson.JsonObject
import com.lookuptalk.model.LoginData
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


/**
 * Created by SUJITH on 19/10/2019
 */
interface RetrofitService {

    @POST("/api/user/authenticate/login")
    fun getlogin(@Body body: JsonObject): Call<LoginData>

    /*Get Flags Data*/
    @get:GET("/api/lookuptalk/flags/offset/0")
    val flagsdata: Call<ResponseBody>

    /*Personal Data*/
    @POST("/api/social/profile")
    fun updatePersonal(@Body body: JsonObject): Call<LoginData>


    /*Occupations Data*/
    @get:GET("/api/master/occupations")
    val getOccupations: Call<ResponseBody>

    /*Interest Data*/
    @GET("/api/master/lifestyle/interests")
    fun getHobiesInterest(@Header("Authorization") authHeader: String): Call<ResponseBody>


    /*SPorts Data*/
    @GET("/api/master/lifestyle/sports")
    fun getSports(@Header("Authorization") authHeader: String): Call<ResponseBody>


    /*countries Data*/
    @GET("/api/master/lifestyle/countries")
    fun getcountries(@Header("Authorization") authHeader: String): Call<ResponseBody>

    /*Fashion Data*/
    @GET("/api/master/lifestyle/fashions")
    fun getfashions(@Header("Authorization") authHeader: String): Call<ResponseBody>


    /*Cusines Data*/
    @GET("/api/master/lifestyle/cuisines")
    fun getcuisines(@Header("Authorization") authHeader: String): Call<ResponseBody>


    /*Pets*/
    @GET("api/master/lifestyle/pets")
    fun getpets(@Header("Authorization") authHeader: String): Call<ResponseBody>



    /*Music*/
    @GET("api/master/lifestyle/musics")
    fun getMusic(@Header("Authorization") authHeader: String): Call<ResponseBody>


    /*Shows*/
    @GET("api/master/lifestyle/shows")
    fun getShows(@Header("Authorization") authHeader: String): Call<ResponseBody>


    /*books*/
    @GET("api/master/lifestyle/books")
    fun getBooks(@Header("Authorization") authHeader: String): Call<ResponseBody>


    /*movies*/
    @GET("api/master/lifestyle/movies")
    fun getMovies(@Header("Authorization") authHeader: String): Call<ResponseBody>

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


    /*Get MoviesNowPlaying*/
    @GET("/3/tv/popular")
    fun getTVShows(@Query("api_key") api_key: String, @Query("language") language: String, @Query("page") page: String): Call<ResponseBody>

    /*Get BooksAPI*/
    @GET("v1/volumes/")
    fun getBooksSearch(@Query("q") search_string: String): Call<ResponseBody>

    /*Get BooksAPI*/
    @GET("v1/volumes/{volumeId}")
    fun getBooksByID(@Path("volumeId") volumeId: String): Call<ResponseBody>

    /*Get MoviesNowPlaying*/
    @GET("/3/search/movie")
    fun getMoviesSearch(
        @Query("api_key") api_key: String, @Query("language") language: String, @Query("query") query: String, @Query("page") page: String): Call<ResponseBody>

    /*Get MoviesNowPlaying*/
    @GET("/3/search/person")
    fun getMovieArtistSearch(
        @Query("api_key") api_key: String, @Query("language") language: String, @Query("query") query: String, @Query("page") page: String): Call<ResponseBody>

    /*Get TVShowsSearch*/
    @GET("/3/search/tv")
    fun getTVShowsSearch(
        @Query("api_key") api_key: String, @Query("language") language: String, @Query("query") query: String, @Query("page") page: String): Call<ResponseBody>

    /*Get MovieFromId*/
    @GET("/3/movie/{movie_id}")
    fun getMovieFromId(@Path("movie_id") movie_id: Long, @Query("api_key") api_key: String, @Query("language") language: String): Call<ResponseBody>

    /*Get MovieFromId*/
    @GET("/3/tv/{tv_id}")
    fun geTVShowsFromId(@Path("tv_id") tv_id: Long, @Query("api_key") api_key: String, @Query("language") language: String): Call<ResponseBody>

    /*Get ArtistFromId*/
    @GET("/3/person/{person_id}")
    fun getArtistFromId(@Path("person_id") person_id: Long, @Query("api_key") api_key: String, @Query("language") language: String): Call<ResponseBody>

    /*Get MoviesNowPlaying*/
    @POST("/api/social")
    fun AddFavoriteMovies(@Body body: JsonObject): Call<ResponseBody>

    /*Get Favorites Data*/
    @GET("/api/social")
    fun getFavoriteMovies(@Header("Authorization") authHeader: String): Call<ResponseBody>

    /*Get Spotify Data*/
    @GET("/v1/me/following")
    fun getSpotify_Artist(@Header("Authorization") authHeader: String, @Query("type") type: String): Call<ResponseBody>

    /*Delete Favorites Data*/
    @HTTP(method = "DELETE", path = "/api/social/lifestyle", hasBody = true)
    fun deleteFavorite(@Body body: JsonObject): Call<ResponseBody>


}
