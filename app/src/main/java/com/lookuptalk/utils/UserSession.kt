package com.lookuptalk.utils

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONObject


class UserSession(private val _context: Context) {

    // Shared Preferences reference
    internal var pref: SharedPreferences

    // Editor reference for Shared preferences
    private val editor: SharedPreferences.Editor

    // Shared preferences mode
    internal var PRIVATE_MODE = 0



    /*Save Token*/

    /*Get APP Token*/
    fun setAppToken(value: String) {
        editor.putString(SET_TOKEN, value)
        editor.apply()
    }

    fun getAppToken(): String {
        return pref.getString(SET_TOKEN, "")!!
    }

    /*Remove Fcm Token*/
    fun removeAppToken() {
        editor.remove(SET_TOKEN)
        editor.apply()
    }


    fun setSelectedMovies(value: JSONObject) {
        editor.putString(SELECTED_MOVIES, value.toString())
        editor.apply()
    }

    fun getSelectedMovies(): String {
        return pref.getString(SELECTED_MOVIES, "")!!
    }

    /*Remove Fcm Token*/
    fun removeSelectedMovies() {
        editor.remove(SELECTED_MOVIES)
        editor.apply()
    }

    fun setLifestyleType(value: JSONObject) {
        editor.putString(LIFESTYLE_TYPE, value.toString())
        editor.apply()
    }

    fun getLifestyleType(): String {
        return pref.getString(LIFESTYLE_TYPE, "")!!
    }

    /*Remove Fcm Token*/
    fun removeLifestyleType() {
        editor.remove(LIFESTYLE_TYPE)
        editor.apply()
    }

    /*Genre Saving*/
    fun setGenreType(value: String) {
        editor.putString(GENRE_TYPE, value)
        editor.apply()
    }

    fun getGenreType(): String {
        return pref.getString(GENRE_TYPE, "")!!
    }

    /*Remove Fcm Token*/
    fun removeGenreType() {
        editor.remove(GENRE_TYPE)
        editor.apply()
    }



    init {
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }



    /*Save PhoneNumber*/
    fun setPhoneNum(value: String) {
        editor.putString(PHONE_ID, value)
        editor.apply()
    }

    fun getPhoneNum(): String {
        return pref.getString(PHONE_ID, "")!!
    }

    /*Vehicle ID*/
    fun removePhoneNum() {
        editor.remove(PHONE_ID)
        editor.apply()
    }


    fun setGoogleLogin(value: String) {
        editor.putString(GOOGLE_LOGIN, value)
        editor.apply()
    }

    fun getGoogleLogin(): String {
        return pref.getString(GOOGLE_LOGIN, "")!!
    }


    fun removeGoogleLogin() {
        editor.remove(GOOGLE_LOGIN)
        editor.apply()
    }

    /*Save Google Login*/
    fun setGoogleData(value: String) {
        editor.putString(GOOGLE_DATA, value)
        editor.apply()
    }

    fun getGoogleData(): String {
        return pref.getString(GOOGLE_DATA, "")!!
    }


    fun removeGoogleData() {
        editor.remove(GOOGLE_DATA)
        editor.apply()
    }


    /*Save Facebook Login*/
    fun setFBData(value: String) {
        editor.putString(FB_DATA, value)
        editor.apply()
    }

    fun getFBData(): String {
        return pref.getString(FB_DATA, "")!!
    }


    fun removeFBData() {
        editor.remove(FB_DATA)
        editor.apply()
    }


    /*Open Task DMS*/
    fun setGetStarted(value: String) {
        editor.putString(GET_STARTED, value)
        editor.apply()
    }

    fun getGetStarted(): String {
        return pref.getString(GET_STARTED, "")!!
    }

    fun removeGetStarted() {
        editor.remove(GET_STARTED)
        editor.apply()
    }

    /*LOGIN Number*/
    fun setLifestylevalue(value: String) {
        editor.putString(LIFESTYLE_OPTION, value)
        editor.apply()
    }


    fun getLifestylevalue(): String {
        return pref.getString(LIFESTYLE_OPTION, "")!!
    }

    fun removesetLifestylevalue() {
        editor.remove(LIFESTYLE_OPTION)
        editor.apply()
    }

    /*IMDB_Search*/
    fun setimdbSearch(value: String) {
        editor.putString(IMDB_SEARCH, value)
        editor.apply()
    }


    fun getimdbSearch(): String {
        return pref.getString(IMDB_SEARCH, "")!!
    }

    fun removeimdbSearch() {
        editor.remove(IMDB_SEARCH)
        editor.apply()
    }

    /*Get SPotify Token*/
    fun setSpotifyToken(value: String) {
        editor.putString(SPOTIFY_TOKEN, value)
        editor.apply()
    }

    fun getSpotifyToken(): String {
        return pref.getString(SPOTIFY_TOKEN, "")!!
    }

    /*Remove Fcm Token*/
    fun removeSpotifyToken() {
        editor.remove(SPOTIFY_TOKEN)
        editor.apply()
    }

    fun setSpotifyLogout(value: String) {
        editor.putString(SPOTIFY_LOGOUT, value)
        editor.apply()
    }

    fun getSpotifyLogout(): String {
        return pref.getString(SPOTIFY_LOGOUT, "")!!
    }

    /*Remove Fcm Token*/
    fun removeSpotifyLogout() {
        editor.remove(SPOTIFY_LOGOUT)
        editor.apply()
    }






    companion object {

        // Shared preferences file location_name
        private val PREFER_NAME = "LOOKUPTALK"


        private val SET_TOKEN = "token"
        private val SELECTED_MOVIES = "selected_movies"
        private val LIFESTYLE_TYPE = "lifestyle_type"
        private val GENRE_TYPE = "genre_Type"

        private val GET_STARTED = "GET_STARTED"
        private val LIFESTYLE_OPTION = "lifestyle_option "
        private val IMDB_SEARCH = "imdb_search "
        private val PHONE_ID = "phone_ID"
        private val GOOGLE_LOGIN = "google_login"
        private val GOOGLE_DATA = "google_data"
        private val FB_DATA = "fb_data"
        private val SPOTIFY_TOKEN = "spotify_token"
        private val SPOTIFY_LOGOUT = "spotify_logout"
    }


}
