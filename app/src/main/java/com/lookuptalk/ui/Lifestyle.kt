package com.lookuptalk.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.widget.NestedScrollView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.*
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.lookuptalk.retrofitservice.RetrofitService
import com.lookuptalk.utils.Constants
import com.lookuptalk.utils.UserSession
import com.lookuptalk.utils.Utilities
import com.lookuptalk.R
import com.lookuptalk.adapter.*
import com.lookuptalk.customfonts.EditeText_bookman_Regular
import com.lookuptalk.customfonts.Ferrara_Bold
import com.lookuptalk.customfonts.MyTextView_Normal
import com.lookuptalk.model.*
import com.lookuptalk.retrofitservice.ServiceBuilder
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import kotlinx.android.synthetic.main.layout_item.mShimmerViewContainer
import kotlinx.android.synthetic.main.lifestyle_drawer.*
import kotlinx.android.synthetic.main.navigation_lifestyle.*
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create


class Lifestyle : AppCompatActivity(), View.OnClickListener {

    private var mRequestQueue: RequestQueue? = null
    private var selected_string: String = ""
    private lateinit var layoutManager: LinearLayoutManager
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigation_rv: RecyclerView
    lateinit var rv_spotifyartist: RecyclerView
    lateinit var rv_imdb: RecyclerView
    lateinit var rv_artist: RecyclerView
    lateinit var rvTVShow_imdb: RecyclerView
    lateinit var rvTV_artist: RecyclerView
    lateinit var rvBooks: RecyclerView
    lateinit var llAddSpotify: LinearLayout
    lateinit var llAddFavArtist: LinearLayout
    lateinit var llAddFavoriteMovies: LinearLayout
    lateinit var llAddFavTVSHow: LinearLayout
    lateinit var llAddFavArtist_TV: LinearLayout
    lateinit var llAddFavBooks: LinearLayout

    private val TAG = "Lifestyle"

    lateinit var llSpotify: LinearLayout
    lateinit var tv_spot_login: ImageView
    lateinit var tv_spot_logout: ImageView
    lateinit var llMoviesIMDB: LinearLayout
    lateinit var llTVShowsIMDB: LinearLayout
    lateinit var llGoogleBooks: LinearLayout
    lateinit var etFilter: EditeText_bookman_Regular

    lateinit var tvtitleLifestyle: Ferrara_Bold
    lateinit var ivImageTitle: ImageView
    lateinit var tvLifestyleSubmit: MyTextView_Normal
    lateinit var tv_go_ahead: MyTextView_Normal
    private var gson: Gson? = null
    private lateinit var gsonObject: JsonObject


    lateinit var nestedscroll: NestedScrollView


    private var listArray: JSONArray? = null
    private var searchitemList: ArrayList<SearchItem>? = null
    private var moviesList: ArrayList<ImdbModel>? = null

    //    public static ArrayList<String> array_sort;
    private var imageID: String? = null
    private var isSelected: String? = null
    private var imagename: String? = null
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var imdbfav_Adapter: IMDBFav_MoviesAdapter
    private lateinit var imdbfavArtist_Adapter: IMDBFav_ArtistAdapter
    private lateinit var tvArtistAdapter: IMDBFav_TVArtistsAdapter
    private lateinit var tvBooksAdapter: GoogleFavBooksAdapter
    private lateinit var tvshows_Adapter: IMDBFav_TVShowssAdapter

    //    private lateinit var imdbartist_Adapter: IMDBFavoriteAdapter
    private lateinit var imdbSearchAdapter: IMDBSearchAdapter
    private lateinit var spotifyAdapter: Spotify_Adapter
    private lateinit var fav_booksAdapter: Fav_BooksSearchAdapter
    private lateinit var cusineListAdapter: CusineListAdapter
    private lateinit var fashionListAdapter: FashionListAdapter
    private var subItem: SubItem? = null
    private var subItemList: ArrayList<SubItem>? = null
    var strSearch: String = ""
    var mSpotifyToken: String = ""
    var mSpotifyLogout: String = ""
    var appToken: String = ""
    var MoviesId: String = ""

    lateinit var selected_movies: List<String>

    /*Selected Lifestyle Image*/
    private lateinit var ivhobbies: ImageView
    private lateinit var ivmusic: ImageView
    private lateinit var ivbooks: ImageView
    private lateinit var ivsports: ImageView
    private lateinit var ivfashion: ImageView

    private lateinit var ivcuisine: ImageView
    private lateinit var ivmovies: ImageView
    private lateinit var ivTvshow: ImageView
    private lateinit var ivcountries: ImageView
    private lateinit var ivpets: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lifestyle_drawer)

        gson = Gson()
        init()

        // Set the toolbar
        setSupportActionBar(activity_main_toolbar)

        appToken = UserSession(this@Lifestyle).getAppToken()
        mSpotifyToken = UserSession(this@Lifestyle).getSpotifyToken()
        mSpotifyLogout = UserSession(this@Lifestyle).getSpotifyLogout()


//        appToken =
//            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c3ItYzM1NTc3OTctYmM3YS00MTQ4LWI5NmItZjdlMzgxOTAxNjdlIiwidXNlclVuaXF1ZUlkIjoidW5pcS05YmEwZGI2Ny1hNjMzLTRjNmQtYjUxNC1lODcyZDk4YWU5ODMiLCJpYXQiOjE2MDE5MTY1OTEsImV4cCI6MTYwMjUyMTM5MX0.Lz_ySCI2wQtXIOmMsk097MA5trxKKA05yO6mzWg6fOY"


        // Setup Recyclerview's Layout
        layoutManager = LinearLayoutManager(this@Lifestyle)


        rv_imdb.setHasFixedSize(true)

        // Update Adapter with item data and highlight the default menu item ('Home' Fragment)
        updateAdapter(0)


        // Close the soft keyboard when you open or close the Drawer
        val toggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout,
            activity_main_toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {
            override fun onDrawerClosed(drawerView: View) {
                // Triggered once the drawer closes
                super.onDrawerClosed(drawerView)
                try {
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }

            override fun onDrawerOpened(drawerView: View) {
                // Triggered once the drawer opens
                super.onDrawerOpened(drawerView)
                try {
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }
        }
        drawerLayout.addDrawerListener(toggle)

        toggle.syncState()



        etFilter.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {

                strSearch = s.toString()
                selected_string = UserSession(this@Lifestyle).getLifestylevalue()

                if (strSearch.length == 0) {

                    if (selected_string.equals("movies") || selected_string.equals("movie_artist")) {
                        etFilter.visibility = View.GONE
                        MoviesUI()
                    } else if (selected_string.equals("tvshows") || selected_string.equals("tvshows_artist")) {
                        etFilter.visibility = View.GONE
                        TVShowsUI()
                    } else if (selected_string.equals("books")) {
                        etFilter.visibility = View.GONE
                        BooksUI()
                    }

                } else {
                    var temp = ArrayList<SearchItem>()

                    for (item in searchitemList!!) {
                        var tempSub = ArrayList<SubItem>()
                        for (sub in item.subItemList) {
                            if (sub.name.toLowerCase().contains(strSearch.toLowerCase())) {
                                tempSub.add(sub)
                            }
                        }
                        if (tempSub.size > 0) {
                            var myItem = SearchItem()
                            myItem.itemTitle = item.itemTitle
                            myItem.subItemList = ArrayList<SubItem>(tempSub)
                            temp.add(myItem)
                        }
                    }


                    if (strSearch.trim().isEmpty()) {
                        temp = searchitemList as ArrayList<SearchItem>
                    }

                    Log.d(TAG, "afterTextChanged() called with: s = $s")

                    if (selected_string.equals("hobbies") || selected_string.equals("pets")
                        || selected_string.equals("countries") || selected_string.equals("sports")
                    ) {

                        itemAdapter = ItemAdapter(this@Lifestyle, temp!!, strSearch)
                        navigation_rv.setAdapter(itemAdapter)

                    } else if (selected_string.equals("cuisine") || selected_string.equals("music")) {
                        cusineListAdapter = CusineListAdapter(this@Lifestyle, temp!!, strSearch)
                        navigation_rv.setAdapter(cusineListAdapter)


                    } else if (selected_string.equals("fashion")) {
                        fashionListAdapter = FashionListAdapter(this@Lifestyle, temp!!, strSearch)
                        navigation_rv.setAdapter(fashionListAdapter)


                    } else if (selected_string.equals("movies")) {
                        layoutManager = LinearLayoutManager(this@Lifestyle)
                        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL)
                        llMoviesIMDB.visibility = View.GONE
                        navigation_rv.visibility = View.VISIBLE
                        getMoviesSearch(strSearch)
                        tvLifestyleSubmit.setText("Add")
                    } else if (selected_string.equals("movie_artist")) {
                        layoutManager = LinearLayoutManager(this@Lifestyle)
                        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL)
                        llMoviesIMDB.visibility = View.GONE
                        navigation_rv.visibility = View.VISIBLE
                        getMovieArtistSearch(strSearch, selected_string)
                        tvLifestyleSubmit.setText("Add")
                    } else if (selected_string.equals("tvshows")) {

                        layoutManager = LinearLayoutManager(this@Lifestyle)
                        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL)
                        llMoviesIMDB.visibility = View.GONE
                        llTVShowsIMDB.visibility = View.GONE
                        navigation_rv.visibility = View.VISIBLE
                        getTVShowsSearch(strSearch)
                        tvLifestyleSubmit.setText("Add")
                    } else if (selected_string.equals("tvshows_artist")) {
                        layoutManager = LinearLayoutManager(this@Lifestyle)
                        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL)
                        llMoviesIMDB.visibility = View.GONE
                        llTVShowsIMDB.visibility = View.GONE
                        navigation_rv.visibility = View.VISIBLE
                        getTVArtistSearch(strSearch, selected_string)
                        tvLifestyleSubmit.setText("Add")
                    } else if (selected_string.equals("books")) {

                        layoutManager = LinearLayoutManager(this@Lifestyle)
                        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL)
                        llGoogleBooks.visibility = View.GONE
                        navigation_rv.visibility = View.VISIBLE
                        getBooksSearch(strSearch)
                        tvLifestyleSubmit.setText("Add")
                    }
                }

            }
        })
        getFavoriteLifestyle()
    }


    private fun updateAdapter(highlightItemPos: Int) {
        //        navigation_rv.adapter = adapter
//        adapter.notifyDataSetChanged()
    }


    fun init() {

        drawerLayout = findViewById(R.id.drawer_layout)

        llSpotify = findViewById(R.id.llSpotify)
        tv_spot_login = findViewById(R.id.tv_spot_login)
        tv_spot_logout = findViewById(R.id.tv_spot_logout)
        llMoviesIMDB = findViewById(R.id.llMoviesIMDB)
        llTVShowsIMDB = findViewById(R.id.llTVShowsIMDB)
        llGoogleBooks = findViewById(R.id.llGoogleBooks)
        navigation_rv = findViewById(R.id.navigation_rv)
        rv_spotifyartist = findViewById(R.id.rv_spotifyartist)
        rv_imdb = findViewById(R.id.rv_imdb)
        rv_artist = findViewById(R.id.rv_artist)
        rvTVShow_imdb = findViewById(R.id.rvTVShow_imdb)
        rvTV_artist = findViewById(R.id.rvTV_artist)
        rvBooks = findViewById(R.id.rv_Books)
        llAddSpotify = findViewById(R.id.llAddSpotify)
        llAddFavArtist = findViewById(R.id.llAddFavArtist)
        llAddFavTVSHow = findViewById(R.id.llAddFavTVSHow)
        llAddFavArtist_TV = findViewById(R.id.llAddFavArtist_TV)
        llAddFavBooks = findViewById(R.id.llAddFavBooks)
        llAddFavoriteMovies = findViewById(R.id.llAddFavoriteMovies)
        nestedscroll = findViewById(R.id.nestedscroll)

        etFilter = findViewById(R.id.etFilter)
        tvtitleLifestyle = findViewById(R.id.tvtitleLifestyle)
        ivImageTitle = findViewById(R.id.ivImageTitle)
        tvLifestyleSubmit = findViewById(R.id.tvLifestyleSubmit)
        tv_go_ahead = findViewById(R.id.tv_go_ahead)

        navigation_rv.setNestedScrollingEnabled(false);

        /*Lock Swipe Drawer*/
//        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.END)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        ivhobbies = findViewById(R.id.ivhobbies)
        ivmusic = findViewById(R.id.ivmusic)
        ivbooks = findViewById(R.id.ivbooks)
        ivsports = findViewById(R.id.ivsports)
        ivfashion = findViewById(R.id.ivfashion)
        ivcuisine = findViewById(R.id.ivcuisine)
        ivmovies = findViewById(R.id.ivmovies)
        ivTvshow = findViewById(R.id.ivTvshow)
        ivcountries = findViewById(R.id.ivcountries)
        ivpets = findViewById(R.id.ivpets)


    }


    override fun onClick(v: View) {
        val i = v.id
        if (i == R.id.llHobbies) {

            tvLifestyleSubmit.setText(resources.getString(R.string.submit))
            etFilter.setHint("")
            llSpotify.visibility = View.GONE
            llMoviesIMDB.visibility = View.GONE
            llTVShowsIMDB.visibility = View.GONE
            llGoogleBooks.visibility = View.GONE
            UserSession(this@Lifestyle).setLifestylevalue("hobbies")

            mShimmerViewContainer!!.visibility = View.VISIBLE
            mShimmerView2!!.visibility = View.GONE

            tvtitleLifestyle.setText(resources.getString(R.string.hobbies_intrest))
            ivImageTitle.setImageResource(R.drawable.ic_hobbies)

            drawerLayout.openDrawer(GravityCompat.END)

            Handler().postDelayed({

                getHobies()
            }, 1000)
            nestedscroll.scrollTo(0, 0);


        } else if (i == R.id.llMusic) {

            if (!mSpotifyToken.equals("")) {

                getSpotifyArtistList(mSpotifyToken)
            } else {
                val request = getAuthenticationRequest(AuthenticationResponse.Type.TOKEN)
                AuthenticationClient.openLoginActivity(
                    this,
                    Constants.AUTH_TOKEN_REQUEST_CODE,
                    request
                )
            }

//            if (mSpotifyLogout.equals("logout")) {
//                tv_spot_logout.visibility = View.VISIBLE
//                tv_spot_login.visibility = View.GONE
//                rv_spotifyartist.visibility = View.GONE
//            } else if(mSpotifyLogout.equals("login")){
//                if (!mSpotifyToken.equals("")) {
//
//                    getSpotifyArtistList(mSpotifyToken)
//                } else {
//                    val request = getAuthenticationRequest(AuthenticationResponse.Type.TOKEN)
//                    AuthenticationClient.openLoginActivity(
//                        this,
//                        Constants.AUTH_TOKEN_REQUEST_CODE,
//                        request
//                    )
//                }
//            }


            tvLifestyleSubmit.setText(resources.getString(R.string.submit))
            etFilter.setHint("")
            llSpotify.visibility = View.VISIBLE
            llMoviesIMDB.visibility = View.GONE
            llTVShowsIMDB.visibility = View.GONE
            llGoogleBooks.visibility = View.GONE
            UserSession(this@Lifestyle).setLifestylevalue("music")

            mShimmerViewContainer!!.visibility = View.GONE
            mShimmerView2!!.visibility = View.VISIBLE


            tvtitleLifestyle.setText(resources.getString(R.string.music))
            ivImageTitle.setImageResource(R.drawable.drum)

            drawerLayout.openDrawer(GravityCompat.END)

            Handler().postDelayed({

                getMusic()
            }, 1000)
            nestedscroll.scrollTo(0, 0);


        } else if (i == R.id.llBooks) {
            etFilter.visibility = View.GONE
            BooksUI()


        } else if (i == R.id.llSports) {

            tvLifestyleSubmit.setText(resources.getString(R.string.submit))
            etFilter.setHint("")
            llSpotify.visibility = View.GONE
            llMoviesIMDB.visibility = View.GONE
            llTVShowsIMDB.visibility = View.GONE
            llGoogleBooks.visibility = View.GONE
            UserSession(this@Lifestyle).setLifestylevalue("sports")

            mShimmerViewContainer!!.visibility = View.VISIBLE
            mShimmerView2!!.visibility = View.GONE

            tvtitleLifestyle.setText(resources.getString(R.string.sports))
            ivImageTitle.setImageResource(R.drawable.ic_sports)

            drawerLayout.openDrawer(GravityCompat.END)

            Handler().postDelayed({

                getSportsData()
            }, 1000)
            nestedscroll.scrollTo(0, 0);


        } else if (i == R.id.llFashion) {

            FashionUI()


        } else if (i == R.id.llCuisine) {
            CuisineUI()

        } else if (i == R.id.llMovies) {

            etFilter.visibility = View.GONE
            layoutManager = LinearLayoutManager(this@Lifestyle)
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL)
            MoviesUI()

        } else if (i == R.id.lltvshows) {
            etFilter.visibility = View.GONE
            layoutManager = LinearLayoutManager(this@Lifestyle)
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL)

            TVShowsUI()


        } else if (i == R.id.llCountrisVisited) {

            tvLifestyleSubmit.setText(resources.getString(R.string.submit))
            etFilter.setHint("")
            llSpotify.visibility = View.GONE
            llMoviesIMDB.visibility = View.GONE
            llTVShowsIMDB.visibility = View.GONE
            llGoogleBooks.visibility = View.GONE
            UserSession(this@Lifestyle).setLifestylevalue("countries")

            mShimmerViewContainer!!.visibility = View.VISIBLE
            mShimmerView2!!.visibility = View.GONE

            tvtitleLifestyle.setText(resources.getString(R.string.countries_visited))
            ivImageTitle.setImageResource(R.drawable.ic_countries)

            drawerLayout.openDrawer(GravityCompat.END)

            Handler().postDelayed({

                getCountriesData()
            }, 1000)
            nestedscroll.scrollTo(0, 0);


        } else if (i == R.id.llPets) {

            tvLifestyleSubmit.text.toString().equals(resources.getString(R.string.submit))
            etFilter.setHint("")
            llSpotify.visibility = View.GONE
            llMoviesIMDB.visibility = View.GONE
            llTVShowsIMDB.visibility = View.GONE
            llGoogleBooks.visibility = View.GONE
            UserSession(this@Lifestyle).setLifestylevalue("pets")

            mShimmerViewContainer!!.visibility = View.VISIBLE
            mShimmerView2!!.visibility = View.GONE

            tvtitleLifestyle.setText(resources.getString(R.string.pets_care))
            ivImageTitle.setImageResource(R.drawable.ic_pets)

            drawerLayout.openDrawer(GravityCompat.END)

            Handler().postDelayed({

                getPets()
            }, 1000)
            nestedscroll.scrollTo(0, 0);


        } else if (i == R.id.tv_spot_logout) {

            rv_spotifyartist.visibility = View.GONE
            llAddSpotify.visibility = View.VISIBLE
            tv_spot_logout.visibility = View.GONE
            tv_spot_login.visibility = View.VISIBLE

//            val request = getAuthenticationRequest(AuthenticationResponse.Type.TOKEN)
//            AuthenticationClient.openLoginActivity(this, Constants.AUTH_TOKEN_REQUEST_CODE, request)
//            rv_spotifyartist.visibility = View.VISIBLE
//            UserSession(this@Lifestyle).setSpotifyLogout("logout")

        } else if (i == R.id.tv_spot_login) {

            if (!mSpotifyToken.equals("")) {

                getSpotifyArtistList(mSpotifyToken)
            } else {
                val request = getAuthenticationRequest(AuthenticationResponse.Type.TOKEN)
                AuthenticationClient.openLoginActivity(
                    this,
                    Constants.AUTH_TOKEN_REQUEST_CODE,
                    request
                )
            }

            rv_spotifyartist.visibility = View.VISIBLE
            llAddSpotify.visibility = View.GONE
            tv_spot_logout.visibility = View.VISIBLE
            tv_spot_login.visibility = View.GONE

        } else if (i == R.id.llAddSpotify) {
            if (!mSpotifyToken.equals("")) {

                getSpotifyArtistList(mSpotifyToken)
            } else {
                val request = getAuthenticationRequest(AuthenticationResponse.Type.TOKEN)
                AuthenticationClient.openLoginActivity(
                    this,
                    Constants.AUTH_TOKEN_REQUEST_CODE,
                    request
                )
            }
            rv_spotifyartist.visibility = View.VISIBLE
            llAddSpotify.visibility = View.GONE
            tv_spot_logout.visibility = View.VISIBLE
            tv_spot_login.visibility = View.GONE

        } else if (i == R.id.ivBackLifstyle) {

            onBackPressed()
        } else if (i == R.id.tv_go_ahead) {

            startActivity(Intent(this@Lifestyle, HomeScreen::class.java))
            overridePendingTransition(R.anim.move_left_enter, R.anim.move_left_exit)
            finish()
        } else if (i == R.id.tvLifestyleSubmit) {

            if (tvLifestyleSubmit.text.toString().equals("Add")) {

                Utilities.hideSoftKeyboard(this@Lifestyle)
                etFilter.setText("")
                gsonObject = JsonObject()
                val selected_movies = UserSession(this@Lifestyle).getSelectedMovies()
                Log.e(TAG, "values****: " + selected_movies)


                if (!selected_movies.equals("")) {
                    val jsonParser = JsonParser()
                    gsonObject = jsonParser.parse(selected_movies) as JsonObject

                    AddFavoriteMovies(gsonObject)
                    UserSession(this@Lifestyle).removeSelectedMovies()
                } else {
                    drawerLayout.closeDrawer(GravityCompat.END)
                }

            } else if (tvLifestyleSubmit.text.toString().equals("Submit")) {
                val selected_type = UserSession(this@Lifestyle).getLifestyleType()

                if (!selected_type.equals("")) {
                    Utilities.hideSoftKeyboard(this@Lifestyle)
                    etFilter.setText("")
                    gsonObject = JsonObject()
                    Log.e(TAG, "values****: " + selected_type)
                    val jsonParser = JsonParser()
                    gsonObject = jsonParser.parse(selected_type) as JsonObject

                    AddFavoriteMovies(gsonObject)
                    UserSession(this@Lifestyle).removeLifestyleType()

//                    drawerLayout.closeDrawer(GravityCompat.END)
                } else {
                    drawerLayout.closeDrawer(GravityCompat.END)

                }
                getFavoriteLifestyle()

            }

        } else if (i == R.id.llAddFavArtist) {

            etFilter.visibility = View.VISIBLE
            UserSession(this@Lifestyle).setLifestylevalue("movie_artist")
            etFilter.requestFocus()
            etFilter.setHint(R.string.add_fav_artist)
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(etFilter, InputMethodManager.SHOW_IMPLICIT)

        } else if (i == R.id.llAddFavoriteMovies) {
            etFilter.visibility = View.VISIBLE
            etFilter.requestFocus();
            etFilter.setHint(R.string.add_fav_movie)
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(etFilter, InputMethodManager.SHOW_IMPLICIT)


        } else if (i == R.id.llAddFavTVSHow) {
            etFilter.visibility=View.VISIBLE
            UserSession(this@Lifestyle).setLifestylevalue("tvshows")
            etFilter.requestFocus()
            etFilter.setHint(R.string.add_fav_show)
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(etFilter, InputMethodManager.SHOW_IMPLICIT)

        } else if (i == R.id.llAddFavArtist_TV) {
            etFilter.visibility=View.VISIBLE
            UserSession(this@Lifestyle).setLifestylevalue("tvshows_artist")
            etFilter.requestFocus()
            etFilter.setHint(R.string.add_fav_artist)
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(etFilter, InputMethodManager.SHOW_IMPLICIT)

        } else if (i == R.id.llAddFavBooks) {
            etFilter.visibility=View.VISIBLE
            UserSession(this@Lifestyle).setLifestylevalue("books")
            etFilter.requestFocus()
            etFilter.setHint(R.string.add_fav_books)
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(etFilter, InputMethodManager.SHOW_IMPLICIT)

        }


    }


    private fun getHobies() {

        Constants.retrofitService.getHobiesInterest(appToken)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>, response: Response<ResponseBody>
                ) {

                    try {
                        val string = response.body()!!.string()

                        val jsonObject = JSONObject(string)
                        val jsonArray = jsonObject.getJSONArray("info")
                        searchitemList = ArrayList()

                        for (i in 0 until jsonArray.length()) {
                            val nameObject: JSONObject = jsonArray.getJSONObject(i)
                            listArray = nameObject.getJSONArray("items")
                            val name = nameObject.getString("category")
                            val item = SearchItem(name, buildSubItemList("hobbiesInterests"))
                            searchitemList?.add(item)
                        }

                        mShimmerViewContainer!!.visibility = View.GONE


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e(
                        "TAG",
                        "onFailure() called with: call = [" + call.request()
                            .url + "], t = [" + t + "]",
                        t
                    )


                }
            })

    }

    private fun getMusic() {

        Constants.retrofitService.getMusic(appToken).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

                try {
                    val string = response.body()!!.string()

                    val jsonObject = JSONObject(string)
                    val jsonArray = jsonObject.getJSONArray("info")
                    searchitemList = ArrayList()

                    for (i in 0 until jsonArray.length()) {
                        val nameObject: JSONObject = jsonArray.getJSONObject(i)
                        listArray = nameObject.getJSONArray("items")
                        val name = nameObject.getString("category")
                        val item = SearchItem(name, cusineSubItemList("music"))
                        searchitemList?.add(item)
                    }


                    mShimmerView2!!.visibility = View.GONE


                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(
                    "TAG",
                    "onFailure() called with: call = [" + call.request()
                        .url + "], t = [" + t + "]",
                    t
                )


            }
        })

    }

    private fun getBooks() {

        Constants.retrofitService.getBooks(appToken).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

                try {
                    val string = response.body()!!.string()

                    val jsonObject = JSONObject(string)
                    val jsonArray = jsonObject.getJSONArray("info")
                    searchitemList = ArrayList()

                    for (i in 0 until jsonArray.length()) {
                        val nameObject: JSONObject = jsonArray.getJSONObject(i)
                        listArray = nameObject.getJSONArray("items")
                        val name = nameObject.getString("category")
                        val item = SearchItem(name, cusineSubItemList("books"))
                        searchitemList?.add(item)
                    }


                    mShimmerView2!!.visibility = View.GONE


                } catch (e: Exception) {
                    e.printStackTrace()
                }


            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(
                    "TAG",
                    "onFailure() called with: call = [" + call.request()
                        .url + "], t = [" + t + "]",
                    t
                )


            }
        })

    }

    private fun getSportsData() {

        Constants.retrofitService.getSports(appToken).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

                try {
                    val string = response.body()!!.string()

                    val jsonObject = JSONObject(string)
                    val jsonArray = jsonObject.getJSONArray("info")
                    searchitemList = ArrayList()

                    for (i in 0 until jsonArray.length()) {
                        val nameObject: JSONObject = jsonArray.getJSONObject(i)
                        listArray = nameObject.getJSONArray("items")
                        val name = nameObject.getString("category")
                        val item = SearchItem(name, buildSubItemList("sports"))
                        searchitemList?.add(item)
                    }


                    mShimmerViewContainer!!.visibility = View.GONE


                } catch (e: Exception) {
                    e.printStackTrace()
                }


            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(
                    "TAG",
                    "onFailure() called with: call = [" + call.request()
                        .url + "], t = [" + t + "]",
                    t
                )


            }
        })

    }

    private fun getFashionData() {

        Constants.retrofitService.getfashions(appToken).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

                try {
                    val string = response.body()!!.string()

                    val jsonObject = JSONObject(string)
                    val jsonArray = jsonObject.getJSONArray("info")
                    searchitemList = ArrayList()

                    for (i in 0 until jsonArray.length()) {
                        val nameObject: JSONObject = jsonArray.getJSONObject(i)
                        listArray = nameObject.getJSONArray("items")
                        val name = nameObject.getString("category")
                        val item = SearchItem(name, fashionSubItemList("fashionBrands"))
                        searchitemList?.add(item)
                    }


                    mShimmerView2!!.visibility = View.GONE


                } catch (e: Exception) {
                    e.printStackTrace()
                }


            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(
                    "TAG", "onFailure() called with: call = [" + call.request()
                        .url + "], t = [" + t + "]", t
                )


            }
        })

    }

    private fun getCusinesData() {

        Constants.retrofitService.getcuisines(appToken).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

                try {
                    val string = response.body()!!.string()

                    val jsonObject = JSONObject(string)
                    val jsonArray = jsonObject.getJSONArray("info")
                    searchitemList = ArrayList()

                    for (i in 0 until jsonArray.length()) {
                        val nameObject: JSONObject = jsonArray.getJSONObject(i)
                        listArray = nameObject.getJSONArray("items")
                        val name = nameObject.getString("category")
                        val item = SearchItem(name, cusineSubItemList("cuisines"))
                        searchitemList?.add(item)
                    }


                    mShimmerView2!!.visibility = View.GONE


                } catch (e: Exception) {
                    e.printStackTrace()
                }


            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(
                    "TAG",
                    "onFailure() called with: call = [" + call.request()
                        .url + "], t = [" + t + "]",
                    t
                )


            }
        })

    }

    private fun getMovies() {

        Constants.retrofitService.getMovies(appToken).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

                try {
                    val string = response.body()!!.string()

                    val jsonObject = JSONObject(string)
                    val jsonArray = jsonObject.getJSONArray("info")
                    searchitemList = ArrayList()

                    for (i in 0 until jsonArray.length()) {
                        val nameObject: JSONObject = jsonArray.getJSONObject(i)
                        listArray = nameObject.getJSONArray("items")
                        val name = nameObject.getString("category")
                        val item = SearchItem(name, cusineSubItemList("movies"))
                        searchitemList?.add(item)
                    }


                    mShimmerView2!!.visibility = View.GONE


                } catch (e: Exception) {
                    e.printStackTrace()
                }


            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(
                    "TAG",
                    "onFailure() called with: call = [" + call.request()
                        .url + "], t = [" + t + "]",
                    t
                )


            }
        })

    }

    private fun getTvShowsData() {

        Constants.retrofitService.getShows(appToken).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

                try {
                    val string = response.body()!!.string()

                    val jsonObject = JSONObject(string)
                    val jsonArray = jsonObject.getJSONArray("info")
                    searchitemList = ArrayList()

                    for (i in 0 until jsonArray.length()) {
                        val nameObject: JSONObject = jsonArray.getJSONObject(i)
                        listArray = nameObject.getJSONArray("items")
                        val name = nameObject.getString("category")
                        val item = SearchItem(name, cusineSubItemList("tvShows"))
                        searchitemList?.add(item)
                    }


                    mShimmerView2!!.visibility = View.GONE


                } catch (e: Exception) {
                    e.printStackTrace()
                }


            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(
                    "TAG",
                    "onFailure() called with: call = [" + call.request()
                        .url + "], t = [" + t + "]",
                    t
                )


            }
        })

    }

    private fun getCountriesData() {

        Constants.retrofitService.getcountries(appToken).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                try {
                    val string = response.body()!!.string()

                    val jsonObject = JSONObject(string)
                    val jsonArray = jsonObject.getJSONArray("info")
                    searchitemList = ArrayList()

                    for (i in 0 until jsonArray.length()) {
                        val nameObject: JSONObject = jsonArray.getJSONObject(i)
                        listArray = nameObject.getJSONArray("items")
                        val name = nameObject.getString("category")
                        val item = SearchItem(name, buildSubItemList("countriesTravelled"))
                        searchitemList?.add(item)
                    }


                    mShimmerViewContainer!!.visibility = View.GONE


                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(
                    "TAG",
                    "onFailure() called with: call = [" + call.request()
                        .url + "], t = [" + t + "]",
                    t
                )


            }
        })

    }

    private fun getPets() {

        Constants.retrofitService.getpets(appToken).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

                try {
                    val string = response.body()!!.string()

                    val jsonObject = JSONObject(string)
                    val jsonArray = jsonObject.getJSONArray("info")
                    searchitemList = ArrayList()

                    for (i in 0 until jsonArray.length()) {
                        val nameObject: JSONObject = jsonArray.getJSONObject(i)
                        listArray = nameObject.getJSONArray("items")
                        val name = nameObject.getString("category")
                        val item = SearchItem(name, buildSubItemList("pets"))
                        searchitemList?.add(item)
                    }


                    mShimmerViewContainer!!.visibility = View.GONE


                } catch (e: Exception) {
                    e.printStackTrace()
                }


            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(
                    "TAG",
                    "onFailure() called with: call = [" + call.request()
                        .url + "], t = [" + t + "]",
                    t
                )


            }
        })

    }

    private fun getFavoriteLifestyle() {

        Constants.retrofitService.getFavoriteMovies(appToken).enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

                try {
                    val string = response.body()!!.string()
                    val jsonobj = JSONObject(string)
                    val jsonobjInfo = jsonobj.getJSONObject("info")
                    val hobbies_Array = jsonobjInfo.getJSONArray("hobbiesInterests")
                    val books_Array = jsonobjInfo.getJSONArray("books")
                    val sports_Array = jsonobjInfo.getJSONArray("sports")
                    val fashion_Array = jsonobjInfo.getJSONArray("fashionBrands")
                    val cuisines_Array = jsonobjInfo.getJSONArray("cuisines")
                    val countries_Array = jsonobjInfo.getJSONArray("countriesTravelled")
                    val pets_Array = jsonobjInfo.getJSONArray("pets")


                    val favMovies_Array = jsonobjInfo.getJSONArray("favMovies")
                    val favArtist_Array = jsonobjInfo.getJSONArray("favArtists")
                    val favTvShows_Array = jsonobjInfo.getJSONArray("favTvShows")
                    val favTvArtists_Array = jsonobjInfo.getJSONArray("favTvArtists")
                    val favBooks_Array = jsonobjInfo.getJSONArray("favBooks")

                    if (hobbies_Array.length() > 0) {
                        ivhobbies.visibility = View.VISIBLE

                    }
                    if (sports_Array.length() > 0) {
                        ivsports.visibility = View.VISIBLE

                    }
                    if (books_Array.length() > 0) {
                        ivbooks.visibility = View.VISIBLE

                    }
                    if (fashion_Array.length() > 0) {
                        ivfashion.visibility = View.VISIBLE

                    }
                    if (cuisines_Array.length() > 0) {

                        ivcuisine.visibility = View.VISIBLE

                    }

                    if (favMovies_Array.length() > 0 || favArtist_Array.length() > 0) {
                        ivmovies.visibility = View.VISIBLE

                    }
                    if (favTvShows_Array.length() > 0 || favArtist_Array.length() > 0) {
                        ivTvshow.visibility = View.VISIBLE

                    }
                    if (countries_Array.length() > 0) {

                        ivcountries.visibility = View.VISIBLE
                    }
                    if (pets_Array.length() > 0) {

                        ivpets.visibility = View.VISIBLE
                    }

                    for (i in 0 until favMovies_Array.length()) {
                        val imdbMoviesList =
                            gson!!.fromJson<List<ImdbFavModel>>(
                                favMovies_Array.toString(),
                                object : TypeToken<List<ImdbFavModel>>() {}.type
                            )

                        imdbfav_Adapter = IMDBFav_MoviesAdapter(this@Lifestyle, imdbMoviesList)
                        rv_imdb.setAdapter(imdbfav_Adapter)
                        rv_imdb.layoutManager = LinearLayoutManager(
                            this@Lifestyle,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )


                    }

                    if (favArtist_Array.length() > 0) {
                        for (i in 0 until favArtist_Array.length()) {
                            val imdbArtistsList =
                                gson!!.fromJson<List<ImdbFavModel>>(
                                    favArtist_Array.toString(),
                                    object : TypeToken<List<ImdbFavModel>>() {}.type
                                )

                            imdbfavArtist_Adapter = IMDBFav_ArtistAdapter(
                                this@Lifestyle,
                                imdbArtistsList
                            )
                            rv_artist.setAdapter(imdbfavArtist_Adapter)
                            rv_artist.layoutManager = LinearLayoutManager(
                                this@Lifestyle,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )


                        }

                        if (favTvShows_Array.length() > 0) {
                            for (i in 0 until favTvShows_Array.length()) {
                                val imdbTVshowList =
                                    gson!!.fromJson<List<ImdbFavModel>>(
                                        favTvShows_Array.toString(),
                                        object : TypeToken<List<ImdbFavModel>>() {}.type
                                    )

                                tvshows_Adapter =
                                    IMDBFav_TVShowssAdapter(this@Lifestyle, imdbTVshowList)
                                rvTVShow_imdb.setAdapter(tvshows_Adapter)
                                rvTVShow_imdb.layoutManager = LinearLayoutManager(
                                    this@Lifestyle,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )


                            }

                        }
                        if (favTvArtists_Array.length() > 0) {
                            for (i in 0 until favTvArtists_Array.length()) {
                                val imdbTVArtistList =
                                    gson!!.fromJson<List<ImdbFavModel>>(
                                        favTvArtists_Array.toString(),
                                        object : TypeToken<List<ImdbFavModel>>() {}.type
                                    )

                                tvArtistAdapter =
                                    IMDBFav_TVArtistsAdapter(this@Lifestyle, imdbTVArtistList)
//                                rvTV_artist.setLayoutManager(layoutManager)
                                rvTV_artist.setAdapter(tvArtistAdapter)
                                rvTV_artist.layoutManager = LinearLayoutManager(
                                    this@Lifestyle,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )


                            }

                        }
                        if (favBooks_Array.length() > 0) {
                            for (i in 0 until favBooks_Array.length()) {
                                val favBookstList =
                                    gson!!.fromJson<List<ImdbFavModel>>(
                                        favBooks_Array.toString(),
                                        object : TypeToken<List<ImdbFavModel>>() {}.type
                                    )

                                tvBooksAdapter =
                                    GoogleFavBooksAdapter(this@Lifestyle, favBookstList)
//                                rvBooks.setLayoutManager(layoutManager)
                                rvBooks.setAdapter(tvBooksAdapter)
                                rvBooks.layoutManager = LinearLayoutManager(
                                    this@Lifestyle,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )


                            }

                        }

                    }

                    mShimmerViewContainer!!.visibility = View.GONE


                } catch (e: Exception) {
                    e.printStackTrace()
                }


            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(
                    "TAG",
                    "onFailure() called with: call = [" + call.request()
                        .url + "], t = [" + t + "]",
                    t
                )


            }
        })

    }


    private fun AddFavoriteMovies(gsonObject: JsonObject) {
        val retrofitService: RetrofitService = ServiceBuilder(this).getRetrofit()!!.create()
        retrofitService.AddFavoriteMovies(gsonObject).enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

                try {
                    val string = response.body()!!.string()
                    Log.e(TAG, "onResponse: " + string)

                    selected_string = UserSession(this@Lifestyle).getLifestylevalue()
                    val genretype = UserSession(this@Lifestyle).getGenreType()
                    getFavoriteLifestyle()
                    if (selected_string.equals("movies") || selected_string.equals("movie_artist")) {
                        MoviesUI()
                    } else if (selected_string.equals("tvshows") || selected_string.equals("tvshows_artist")) {
                        TVShowsUI()
                    } else if (selected_string.equals("books")) {
                        BooksUI()
                    } else if (selected_string.equals("cuisine")) {
                        drawerLayout.closeDrawer(GravityCompat.END)
                    } else if (selected_string.equals("hobbies")) {
                        drawerLayout.closeDrawer(GravityCompat.END)
                    } else if (selected_string.equals("fashion")) {
                        drawerLayout.closeDrawer(GravityCompat.END)
                    }


                } catch (e: Exception) {
                    e.printStackTrace()
                }


            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(
                    "TAG",
                    "onFailure() called with: call = [" + call.request().url + "], t = [" + t + "]",
                    t
                )


            }
        })

    }


    private fun getMoviesSearch(strSearch: String) {

        Constants.Imdb_service.getMoviesSearch(Constants.API_Key, "en-US", strSearch, "1")
            .enqueue(object :
                Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {

                    try {
                        val string = response.body()!!.string()
                        Log.e(TAG, "onResponse: " + string)

                        val jsonObject = JSONObject(string)
                        val jsonArray = jsonObject.getJSONArray("results")

                        for (i in 0 until jsonArray.length()) {
                            val imdbMoviesList = gson!!.fromJson<List<ImdbModel>>(
                                jsonArray.toString(),
                                object : TypeToken<List<ImdbModel>>() {}.type
                            )

                            imdbSearchAdapter =
                                IMDBSearchAdapter(this@Lifestyle, imdbMoviesList, "movies")
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL)
                            navigation_rv.setAdapter(imdbSearchAdapter)
                            navigation_rv.layoutManager = LinearLayoutManager(
                                this@Lifestyle,
                                LinearLayoutManager.VERTICAL,
                                false
                            )


                        }




                        mShimmerViewContainer!!.visibility = View.GONE


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }


                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e(
                        "TAG",
                        "onFailure() called with: call = [" + call.request()
                            .url + "], t = [" + t + "]",
                        t
                    )


                }
            })

    }

    private fun getMovieArtistSearch(strSearch: String, MovieType: String) {

        Constants.Imdb_service.getMovieArtistSearch(Constants.API_Key, "en-US", strSearch, "1")
            .enqueue(object :
                Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {

                    try {
                        val string = response.body()!!.string()
                        Log.e(TAG, "onResponse: " + string)

                        val jsonObject = JSONObject(string)
                        val jsonArray = jsonObject.getJSONArray("results")

                        for (i in 0 until jsonArray.length()) {
                            val imdbMoviesList = gson!!.fromJson<List<ImdbModel>>(
                                jsonArray.toString(),
                                object : TypeToken<List<ImdbModel>>() {}.type
                            )

                            imdbSearchAdapter = IMDBSearchAdapter(
                                this@Lifestyle,
                                imdbMoviesList, MovieType
                            )
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL)

                            navigation_rv.setAdapter(imdbSearchAdapter)
                            navigation_rv.layoutManager = LinearLayoutManager(
                                this@Lifestyle,
                                LinearLayoutManager.VERTICAL,
                                false
                            )


                        }




                        mShimmerViewContainer!!.visibility = View.GONE


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }


                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e(
                        "TAG",
                        "onFailure() called with: call = [" + call.request()
                            .url + "], t = [" + t + "]",
                        t
                    )


                }
            })

    }

    private fun getTVArtistSearch(strSearch: String, MovieType: String) {

        Constants.Imdb_service.getMovieArtistSearch(Constants.API_Key, "en-US", strSearch, "1")
            .enqueue(object :
                Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {

                    try {
                        val string = response.body()!!.string()
                        Log.e(TAG, "onResponse: " + string)

                        val jsonObject = JSONObject(string)
                        val jsonArray = jsonObject.getJSONArray("results")

                        for (i in 0 until jsonArray.length()) {
                            val imdbMoviesList = gson!!.fromJson<List<ImdbModel>>(
                                jsonArray.toString(),
                                object : TypeToken<List<ImdbModel>>() {}.type
                            )

                            imdbSearchAdapter =
                                IMDBSearchAdapter(this@Lifestyle, imdbMoviesList, MovieType)
                            navigation_rv.setAdapter(imdbSearchAdapter)
                            navigation_rv.layoutManager = LinearLayoutManager(
                                this@Lifestyle,
                                LinearLayoutManager.VERTICAL,
                                false
                            )


                        }




                        mShimmerViewContainer!!.visibility = View.GONE


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }


                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e(
                        "TAG",
                        "onFailure() called with: call = [" + call.request()
                            .url + "], t = [" + t + "]",
                        t
                    )


                }
            })

    }

    private fun getTVShowsSearch(strSearch: String) {

        Constants.Imdb_service.getTVShowsSearch(Constants.API_Key, "en-US", strSearch, "1")
            .enqueue(object :
                Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {

                    try {
                        val string = response.body()!!.string()
                        Log.e(TAG, "onResponse: " + string)

                        val jsonObject = JSONObject(string)
                        val jsonArray = jsonObject.getJSONArray("results")

                        for (i in 0 until jsonArray.length()) {
                            val imdbMoviesList = gson!!.fromJson<List<ImdbModel>>(
                                jsonArray.toString(),
                                object : TypeToken<List<ImdbModel>>() {}.type
                            )

                            imdbSearchAdapter = IMDBSearchAdapter(
                                this@Lifestyle,
                                imdbMoviesList,
                                "tvshows"
                            )

                            navigation_rv.setAdapter(imdbSearchAdapter)
                            navigation_rv.layoutManager = LinearLayoutManager(
                                this@Lifestyle,
                                LinearLayoutManager.VERTICAL,
                                false
                            )


                        }




                        mShimmerViewContainer!!.visibility = View.GONE


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }


                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e(
                        "TAG",
                        "onFailure() called with: call = [" + call.request()
                            .url + "], t = [" + t + "]",
                        t
                    )


                }
            })

    }

    private fun getBooksSearch(strSearch: String) {

        Constants.GoogleBooks_service.getBooksSearch(strSearch)
            .enqueue(object :
                Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {

                    try {
                        val string = response.body()!!.string()
//                        Log.e(TAG, "onResponse: " + string)

                        val jsonObject = JSONObject(string)
                        val jsonArray = jsonObject.getJSONArray("items")
                        for (i in 0 until jsonArray.length()) {
                            val bookslist = gson!!.fromJson<List<GoogleBooks>>(
                                jsonArray.toString(),
                                object : TypeToken<List<GoogleBooks>>() {}.type
                            )

                            fav_booksAdapter = Fav_BooksSearchAdapter(this@Lifestyle, bookslist)
//                         `   layoutManager.setOrientation(LinearLayoutManager.VERTICAL)
//                            navigation_rv.setLayoutManager(layoutManager)`
                            navigation_rv.setAdapter(fav_booksAdapter)
                            navigation_rv.layoutManager = LinearLayoutManager(
                                this@Lifestyle,
                                LinearLayoutManager.VERTICAL,
                                false
                            )


                        }
                        mShimmerViewContainer!!.visibility = View.GONE


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }


                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e(
                        "TAG",
                        "onFailure() called with: call = [" + call.request()
                            .url + "], t = [" + t + "]",
                        t
                    )


                }
            })

    }


    /*Call MOvies UI*/
    fun MoviesUI() {
        tvLifestyleSubmit.setText(resources.getString(R.string.submit))

        etFilter.setHint(resources.getString(R.string.moviestext_hint))
        llSpotify.visibility = View.GONE
        llMoviesIMDB.visibility = View.VISIBLE
        llTVShowsIMDB.visibility = View.GONE
        llGoogleBooks.visibility = View.GONE
        UserSession(this@Lifestyle).setLifestylevalue("movies")

        mShimmerViewContainer!!.visibility = View.GONE
        mShimmerView2!!.visibility = View.VISIBLE

        tvtitleLifestyle.setText(resources.getString(R.string.movies))
        ivImageTitle.setImageResource(R.drawable.ic_movies)

        drawerLayout.openDrawer(GravityCompat.END)

        Handler().postDelayed({

            getMovies()
        }, 1000)
        nestedscroll.scrollTo(0, 0);
    }

    fun TVShowsUI() {
        tvLifestyleSubmit.setText(resources.getString(R.string.submit))
        etFilter.setHint("")
        llSpotify.visibility = View.GONE
        llMoviesIMDB.visibility = View.GONE
        llGoogleBooks.visibility = View.GONE
        llTVShowsIMDB.visibility = View.VISIBLE

        UserSession(this@Lifestyle).setLifestylevalue("tvshows")

        mShimmerViewContainer!!.visibility = View.GONE
        mShimmerView2!!.visibility = View.VISIBLE

        tvtitleLifestyle.setText(resources.getString(R.string.tvshows))
        ivImageTitle.setImageResource(R.drawable.ic_tv_show)

        drawerLayout.openDrawer(GravityCompat.END)

        Handler().postDelayed({

            getTvShowsData()
        }, 1000)
        nestedscroll.scrollTo(0, 0);
    }

    fun BooksUI() {
        tvLifestyleSubmit.setText(resources.getString(R.string.submit))
        etFilter.setHint("")
        llSpotify.visibility = View.GONE
        llMoviesIMDB.visibility = View.GONE
        llTVShowsIMDB.visibility = View.GONE
        llGoogleBooks.visibility = View.VISIBLE
        navigation_rv.visibility = View.VISIBLE

        UserSession(this@Lifestyle).setLifestylevalue("books")
        mShimmerViewContainer!!.visibility = View.GONE
        mShimmerView2!!.visibility = View.VISIBLE

        tvtitleLifestyle.setText(resources.getString(R.string.books))
        ivImageTitle.setImageResource(R.drawable.ic_books)

        drawerLayout.openDrawer(GravityCompat.END)

        Handler().postDelayed({

            getBooks()
        }, 1000)
        nestedscroll.scrollTo(0, 0);
    }

    fun CuisineUI() {
        tvLifestyleSubmit.setText(resources.getString(R.string.submit))
        etFilter.setHint("")
        llSpotify.visibility = View.GONE
        llMoviesIMDB.visibility = View.GONE
        llTVShowsIMDB.visibility = View.GONE
        llGoogleBooks.visibility = View.GONE
        UserSession(this@Lifestyle).setLifestylevalue("cuisine")

        mShimmerViewContainer!!.visibility = View.GONE
        mShimmerView2!!.visibility = View.VISIBLE

        tvtitleLifestyle.setText(resources.getString(R.string.cusine))
        ivImageTitle.setImageResource(R.drawable.ic_cuisine)

        drawerLayout.openDrawer(GravityCompat.END)

        Handler().postDelayed({

            getCusinesData()
        }, 1000)
        nestedscroll.scrollTo(0, 0);

    }

    fun FashionUI() {
        tvLifestyleSubmit.setText(resources.getString(R.string.submit))
        etFilter.setHint("")
        llSpotify.visibility = View.GONE
        llMoviesIMDB.visibility = View.GONE
        llTVShowsIMDB.visibility = View.GONE
        llGoogleBooks.visibility = View.GONE
        UserSession(this@Lifestyle).setLifestylevalue("fashion")

        mShimmerViewContainer!!.visibility = View.GONE
        mShimmerView2!!.visibility = View.VISIBLE


        tvtitleLifestyle.setText(resources.getString(R.string.fashion_brand))
        ivImageTitle.setImageResource(R.drawable.dress)

        drawerLayout.openDrawer(GravityCompat.END)

        Handler().postDelayed({

            getFashionData()
        }, 1000)
        nestedscroll.scrollTo(0, 0);

    }

    /*Get Spotify List*/
    private fun getSpotifyArtistList(token: String) {

        Constants.spotify_service.getSpotify_Artist("Bearer " + token, "artist").enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

                try {
                    val string = response.body()!!.string()
                    Log.e(TAG, "onResponse_spotify: " + string)
                    val jsonObject = JSONObject(string)
                    val artistObject = jsonObject.getJSONObject("artists")
                    val artistArray = artistObject.getJSONArray("items")

                    for (i in 0 until artistArray.length()) {
                        val spotifyList =
                            gson!!.fromJson<List<SpotifyModel>>(
                                artistArray.toString(),
                                object : TypeToken<List<SpotifyModel>>() {}.type
                            )

                        spotifyAdapter = Spotify_Adapter(
                            this@Lifestyle, spotifyList
                        )
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL)

                        rv_spotifyartist.setAdapter(spotifyAdapter)
                        rv_spotifyartist.layoutManager = LinearLayoutManager(
                            this@Lifestyle,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        rv_spotifyartist.visibility = View.VISIBLE
                        llAddSpotify.visibility = View.GONE
                        tv_spot_login.visibility = View.GONE
                        tv_spot_logout.visibility = View.VISIBLE
                    }


                } catch (e: Exception) {
                    e.printStackTrace()
                }


            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(
                    "TAG",
                    "onFailure() called with: call = [" + call.request().url + "], t = [" + t + "]",
                    t
                )

            }
        })

    }


    /*get Inner RecyclerView Data*/
    private fun buildSubItemList(lifestyle_type: String): List<SubItem?>? {
        subItemList = ArrayList<SubItem>()
        try {
            for (j in 0 until listArray!!.length()) {
                val dataObject = listArray!!.getJSONObject(j)
                val imageurl = dataObject.getString("url")
                imagename = dataObject.getString("name")
                imageID = dataObject.getString("_id")
                isSelected = dataObject.getString("isSelected")
                subItem = SubItem(imageID, imageurl, imagename, isSelected)
                subItemList!!.add(subItem!!)
            }
            itemAdapter = ItemAdapter(this@Lifestyle, searchitemList!!, lifestyle_type)
            navigation_rv.setAdapter(itemAdapter)
//            navigation_rv.setLayoutManager(layoutManager)
            navigation_rv.layoutManager =
                LinearLayoutManager(this@Lifestyle, LinearLayoutManager.VERTICAL, false)


        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return subItemList
    }


    private fun cusineSubItemList(lifestyle_type: String): List<SubItem?>? {
        subItemList = ArrayList<SubItem>()
        try {
            for (j in 0 until listArray!!.length()) {
                val dataObject = listArray!!.getJSONObject(j)
                val imageurl = dataObject.getString("url")
                imagename = dataObject.getString("name")
                imageID = dataObject.getString("_id")
                isSelected = dataObject.getString("isSelected")
                subItem = SubItem(imageID, imageurl, imagename, isSelected)
                subItemList!!.add(subItem!!)
            }
            cusineListAdapter = CusineListAdapter(this@Lifestyle, searchitemList!!, lifestyle_type)
            navigation_rv.setAdapter(cusineListAdapter)
            navigation_rv.layoutManager =
                LinearLayoutManager(this@Lifestyle, LinearLayoutManager.VERTICAL, false)


        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return subItemList
    }


    private fun fashionSubItemList(type: String): List<SubItem?>? {
        subItemList = ArrayList<SubItem>()
        try {
            for (j in 0 until listArray!!.length()) {
                val dataObject = listArray!!.getJSONObject(j)
                val imageurl = dataObject.getString("url")
                imagename = dataObject.getString("name")
                imageID = dataObject.getString("_id")
                isSelected = dataObject.getString("isSelected")
                subItem = SubItem(imageID, imageurl, imagename, isSelected)
                subItemList!!.add(subItem!!)
            }
            fashionListAdapter = FashionListAdapter(this@Lifestyle, searchitemList!!, type)
            navigation_rv.setAdapter(fashionListAdapter)
            navigation_rv.layoutManager =
                LinearLayoutManager(this@Lifestyle, LinearLayoutManager.VERTICAL, false)


        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return subItemList
    }


    override fun onBackPressed() {


        val i = Intent(this@Lifestyle, PersonalDetailsActivity::class.java)
        startActivity(i)
        overridePendingTransition(R.anim.move_left_enter, R.anim.move_left_exit)
        finish();

//        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
//            drawerLayout.closeDrawer(GravityCompat.END)
//        } else {
//            // Checking for fragment count on back stack
//            if (supportFragmentManager.backStackEntryCount > 0) {
//                // Go to the previous fragment
//                supportFragmentManager.popBackStack()
//            } else {
//                // Exit the app
//                super.onBackPressed()
//            }
//        }
    }

    /*Delete Funtion Refresh*/
    /*Delete*/
    fun deleteFavorites(gsonObject: JsonObject) {
        val retrofitService: RetrofitService =
            ServiceBuilder(this@Lifestyle).getRetrofit()!!.create()
        retrofitService.deleteFavorite(gsonObject).enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

                try {
                    val string = response.body()!!.string()
                    val jsonobj = JSONObject(string)
                    val descrip = jsonobj.getString("description")
                    val info = jsonobj.getString("info")
                    if (descrip.equals("SUCCESS")) {
                        Toast.makeText(this@Lifestyle, info, Toast.LENGTH_SHORT).show()
                        getFavoriteLifestyle()
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }


            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(
                    "TAG",
                    "onFailure() called with: call = [" + call.request().url + "], t = [" + t + "]",
                    t
                )
            }
        })

    }

    /*Spotify*/
    private fun getAuthenticationRequest(type: AuthenticationResponse.Type): AuthenticationRequest {
        return AuthenticationRequest.Builder(Constants.CLIENT_ID, type, Constants.REDIRECT_URI)
            .setShowDialog(false)
            .setScopes(arrayOf("user-read-email", "user-follow-read"))
            .build()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Constants.AUTH_TOKEN_REQUEST_CODE == requestCode) {
            val response = AuthenticationClient.getResponse(resultCode, data)
            val accessToken: String? = response.accessToken

            Log.e(TAG, "Spotify_token: " + accessToken)
            UserSession(this@Lifestyle).setSpotifyToken(accessToken.toString())
            getSpotifyArtistList(accessToken.toString())
        }
    }


}