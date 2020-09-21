package com.lookuptalk.ui

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.widget.NestedScrollView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lb.utils.Constants
import com.lookuptalk.R
import com.lookuptalk.adapter.CusineAdapter
import com.lookuptalk.adapter.FashionAdapter
import com.lookuptalk.adapter.FlagListAdapter
import com.lookuptalk.adapter.HobbiesAdapter
import com.lookuptalk.customfonts.Ferrara_Bold
import com.lookuptalk.customfonts.MyTextView_Normal
import com.lookuptalk.model.Hobbies
import com.lookuptalk.model.ModelFlag
import kotlinx.android.synthetic.main.lifestyle_drawer.*
import kotlinx.android.synthetic.main.lifestyle_navigation.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Lifestyle_Activity : AppCompatActivity(), View.OnClickListener {

    lateinit var drawerLayout: DrawerLayout
    lateinit var navigation_rv: RecyclerView
    lateinit var rv_indoor: RecyclerView
    lateinit var rv_outdoor: RecyclerView
    lateinit var hobbies_layout: LinearLayout
    lateinit var llSpotify: LinearLayout
    lateinit var fashion_layout: LinearLayout
    lateinit var etFilter: EditText
    lateinit var llHobbies: LinearLayout
    lateinit var llSports: LinearLayout
    lateinit var llCuisine: LinearLayout
    lateinit var tvtitleLifestyle: Ferrara_Bold
    lateinit var ivImageTitle: ImageView
    lateinit var tvLifestyleSubmit: MyTextView_Normal
    private lateinit var adapter: FlagListAdapter
    private lateinit var hobies_adapter: HobbiesAdapter
    private lateinit var indoor_adapter: HobbiesAdapter
    private lateinit var outdoor_adapter: HobbiesAdapter
    private lateinit var popular_adapter: HobbiesAdapter
    private var gson: Gson? = null

    lateinit var rv_popular: RecyclerView
    lateinit var rv_premium: RecyclerView
    lateinit var rv_casual: RecyclerView
    lateinit var rv_sports: RecyclerView
    lateinit var rv_designer: RecyclerView
    lateinit var rv_official: RecyclerView
    lateinit var rv_makeup: RecyclerView

    lateinit var tvTitle1: Ferrara_Bold
    lateinit var tvTitle2: Ferrara_Bold
    lateinit var tvTitle3: Ferrara_Bold

    lateinit var tvT1: Ferrara_Bold
    lateinit var tvT2: Ferrara_Bold
    lateinit var tvT3: Ferrara_Bold
    lateinit var tvT4: Ferrara_Bold
    lateinit var tvT5: Ferrara_Bold
    lateinit var tvT6: Ferrara_Bold
    lateinit var tvT7: Ferrara_Bold


    lateinit var nestedscroll: NestedScrollView
    lateinit var llalllistview: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lifestyle_drawer)


        gson = Gson()
        init()


        // Set the toolbar
        setSupportActionBar(activity_main_toolbar)


        // Setup Recyclerview's Layout
        navigation_rv.layoutManager = GridLayoutManager(this, 3)
        rv_indoor.layoutManager = GridLayoutManager(this, 3)
        rv_outdoor.layoutManager = GridLayoutManager(this, 3)




        navigation_rv.setHasFixedSize(true)
        rv_indoor.setHasFixedSize(true)
        rv_outdoor.setHasFixedSize(true)

        rv_popular.setHasFixedSize(true)
        rv_premium.setHasFixedSize(true)
        rv_casual.setHasFixedSize(true)
        rv_sports.setHasFixedSize(true)
        rv_designer.setHasFixedSize(true)
        rv_official.setHasFixedSize(true)
        rv_makeup.setHasFixedSize(true)


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


        // Set Header Image
//        navigation_header_img.setImageResource(R.drawable.logo)

        // Set background of Drawer
    }

    private fun updateAdapter(highlightItemPos: Int) {
//        adapter = FlagListAdapter(this@Lifestyle_Activity)
//        navigation_rv.adapter = adapter
//        adapter.notifyDataSetChanged()
    }


    fun init() {

        llalllistview = findViewById(R.id.llalllistview)
        drawerLayout = findViewById(R.id.drawer_layout)
        nestedscroll = findViewById(R.id.nestedscroll)
        llHobbies = findViewById(R.id.llHobbies)
        llSports = findViewById(R.id.llSports)
        llCuisine = findViewById(R.id.llCuisine)
        navigation_rv = findViewById(R.id.navigation_rv)
        hobbies_layout = findViewById(R.id.hobbies_layout)
        llSpotify = findViewById(R.id.llSpotify)
        fashion_layout = findViewById(R.id.fashion_layout)
        rv_indoor = findViewById(R.id.rv_indoor)
        rv_outdoor = findViewById(R.id.rv_outdoor)
        etFilter = findViewById(R.id.etFilter)
        tvtitleLifestyle = findViewById(R.id.tvtitleLifestyle)
        ivImageTitle = findViewById(R.id.ivImageTitle)
        tvLifestyleSubmit = findViewById(R.id.tvLifestyleSubmit)

        tvTitle1 = findViewById(R.id.tvTitle1)
        tvTitle2 = findViewById(R.id.tvTitle2)
        tvTitle3 = findViewById(R.id.tvTitle3)

        tvT1 = findViewById(R.id.tvT1)
        tvT2 = findViewById(R.id.tvT2)
        tvT3 = findViewById(R.id.tvT3)
        tvT4 = findViewById(R.id.tvT4)
        tvT5 = findViewById(R.id.tvT5)
        tvT6 = findViewById(R.id.tvT6)
        tvT7 = findViewById(R.id.tvT7)

        rv_popular = findViewById(R.id.rv_popular)
        rv_premium = findViewById(R.id.rv_premium)
        rv_casual = findViewById(R.id.rv_casual)
        rv_sports = findViewById(R.id.rv_sports)
        rv_designer = findViewById(R.id.rv_designer)
        rv_official = findViewById(R.id.rv_official)
        rv_makeup = findViewById(R.id.rv_makeup)

        navigation_rv.setNestedScrollingEnabled(false);
        rv_outdoor.setNestedScrollingEnabled(false);
        rv_indoor.setNestedScrollingEnabled(false);

        rv_popular.setNestedScrollingEnabled(false);
        rv_premium.setNestedScrollingEnabled(false);
        rv_casual.setNestedScrollingEnabled(false);
        rv_sports.setNestedScrollingEnabled(false);
        rv_designer.setNestedScrollingEnabled(false);
        rv_official.setNestedScrollingEnabled(false);
        rv_makeup.setNestedScrollingEnabled(false);


        /*Lock Swipe Drawer*/
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.END);



        getMusic()
        getBooks()
        getSportsData()
        getFashionData()
        getCusinesData()
        getMovies()
        getTvShowsData()



    }


    override fun onClick(v: View) {
        val i = v.id
        if (i == R.id.llHobbies) {

             llalllistview.visibility = View.GONE
           
            mShimmerViewContainer!!.visibility = View.VISIBLE
            mShimmerView2!!.visibility = View.GONE

//

            tvTitle1.visibility = View.VISIBLE
            tvTitle1.visibility = View.VISIBLE
            tvTitle2.visibility = View.VISIBLE
            tvTitle3.visibility = View.VISIBLE
            llSpotify.visibility = View.GONE
            hobbies_layout.visibility = View.VISIBLE
            fashion_layout.visibility = View.GONE
            rv_indoor.visibility = View.VISIBLE
            rv_outdoor.visibility = View.VISIBLE
            tvTitle1.setText(resources.getString(R.string.popular))
            tvTitle2.setText(resources.getString(R.string.outdoor))
            tvTitle3.setText(resources.getString(R.string.indoor))

            tvtitleLifestyle.setText(resources.getString(R.string.hobbies_intrest))
            ivImageTitle.setImageResource(R.drawable.ic_hobbies)

            drawerLayout.openDrawer(GravityCompat.END)

            Handler().postDelayed({

                getHobies()
            }, 1000)
            nestedscroll.scrollTo(0, 0);


        } else if (i == R.id.llSports) {

             llalllistview.visibility = View.GONE
           
            mShimmerViewContainer!!.visibility = View.VISIBLE
            mShimmerView2!!.visibility = View.GONE

            llSpotify.visibility = View.GONE
            tvTitle1.visibility = View.VISIBLE
            tvTitle2.visibility = View.VISIBLE
            tvTitle3.visibility = View.VISIBLE
            hobbies_layout.visibility = View.VISIBLE
            fashion_layout.visibility = View.GONE
            rv_indoor.visibility = View.VISIBLE
            rv_outdoor.visibility = View.VISIBLE

            tvTitle1.setText(resources.getString(R.string.popular))
            tvTitle2.setText(resources.getString(R.string.outdoor))
            tvTitle3.setText(resources.getString(R.string.indoor))

            tvtitleLifestyle.setText(resources.getString(R.string.sports))
            ivImageTitle.setImageResource(R.drawable.ic_golf)
            drawerLayout.openDrawer(GravityCompat.END)
            Handler().postDelayed({

                getSportsData()
            }, 1000)

            nestedscroll.scrollTo(0, 0);


        } else if (i == R.id.llCuisine) {


            llalllistview.visibility = View.GONE
           
            mShimmerViewContainer!!.visibility = View.GONE
            mShimmerView2!!.visibility = View.VISIBLE
            


            tvTitle1.visibility = View.VISIBLE
            tvTitle2.visibility = View.VISIBLE
            tvTitle3.visibility = View.VISIBLE
            llSpotify.visibility = View.GONE
//            rv_indoor.layoutManager = GridLayoutManager(this, 2)

            hobbies_layout.visibility = View.VISIBLE
            fashion_layout.visibility = View.GONE
            rv_indoor.visibility = View.VISIBLE
            rv_outdoor.visibility = View.VISIBLE

            tvTitle1.setText(resources.getString(R.string.popular))
            tvTitle2.setText(resources.getString(R.string.global))
            tvTitle3.setText(resources.getString(R.string.regional))

            tvtitleLifestyle.setText(resources.getString(R.string.cusine))
            ivImageTitle.setImageResource(R.drawable.ic_cuisine)
            drawerLayout.openDrawer(GravityCompat.END)

            Handler().postDelayed({

                getCusinesData()
            }, 1000)

            nestedscroll.scrollTo(0, 0);


        } else if (i == R.id.llFashion) {

             llalllistview.visibility = View.GONE
           
            mShimmerViewContainer!!.visibility = View.GONE
            mShimmerView2!!.visibility = View.VISIBLE
            


            hobbies_layout.visibility = View.GONE
            fashion_layout.visibility = View.VISIBLE
            llSpotify.visibility = View.GONE

            tvtitleLifestyle.setText(resources.getString(R.string.fashion_brand))
            ivImageTitle.setImageResource(R.drawable.dress)
            drawerLayout.openDrawer(GravityCompat.END)

            Handler().postDelayed({

                getFashionData()
            }, 1000)


            nestedscroll.scrollTo(0, 0);
//            readFas hion()


        } else if (i == R.id.llMusic) {

//             llalllistview.visibility = View.GONE
            mShimmerViewContainer!!.visibility = View.GONE
            mShimmerView2!!.visibility = View.VISIBLE
            


            llSpotify.visibility = View.VISIBLE
            hobbies_layout.visibility = View.VISIBLE
            fashion_layout.visibility = View.GONE
            tvTitle1.visibility = View.VISIBLE
            tvtitleLifestyle.setText(resources.getString(R.string.music))
            ivImageTitle.setImageResource(R.drawable.drum)

            tvTitle1.setText(resources.getString(R.string.genre))
            drawerLayout.openDrawer(GravityCompat.END)
            Handler().postDelayed({

                getMusic()
            }, 1000)

//            readFas hion()
            nestedscroll.scrollTo(0, 0);


        } else if (i == R.id.llBooks) {

             llalllistview.visibility = View.GONE
           
            mShimmerViewContainer!!.visibility = View.GONE
            mShimmerView2!!.visibility = View.VISIBLE


            llSpotify.visibility = View.GONE
            hobbies_layout.visibility = View.VISIBLE
            fashion_layout.visibility = View.GONE

            tvTitle1.visibility = View.GONE
            tvTitle2.visibility = View.GONE
            tvTitle3.visibility = View.GONE

            tvtitleLifestyle.setText(resources.getString(R.string.books))
            ivImageTitle.setImageResource(R.drawable.ic_books)
            tvTitle1.setText(resources.getString(R.string.genre))

            drawerLayout.openDrawer(GravityCompat.END)
            Handler().postDelayed({

                getBooks()
            }, 1000)


//            readFas hion()
            nestedscroll.scrollTo(0, 0);


        } else if (i == R.id.llMovies) {

             llalllistview.visibility = View.GONE
           
            mShimmerViewContainer!!.visibility = View.GONE
            mShimmerView2!!.visibility = View.VISIBLE
            


            llSpotify.visibility = View.GONE
            hobbies_layout.visibility = View.VISIBLE
            fashion_layout.visibility = View.GONE

            tvTitle1.visibility = View.GONE
            tvTitle2.visibility = View.GONE
            tvTitle3.visibility = View.GONE

            tvtitleLifestyle.setText(resources.getString(R.string.movies))
            ivImageTitle.setImageResource(R.drawable.ic_movies)

            tvTitle1.setText(resources.getString(R.string.genre))

            drawerLayout.openDrawer(GravityCompat.END)
            Handler().postDelayed({

                getMovies()
            }, 1000)

//            readFas hion()
            nestedscroll.scrollTo(0, 0);


        } else if (i == R.id.llCountrisVisited) {

             llalllistview.visibility = View.GONE
            mShimmerViewContainer!!.startShimmer()
            mShimmerViewContainer!!.visibility = View.VISIBLE
            mShimmerView2!!.visibility = View.GONE


            llSpotify.visibility = View.GONE
            tvTitle2.visibility = View.VISIBLE
            tvTitle3.visibility = View.VISIBLE


            hobbies_layout.visibility = View.GONE
            fashion_layout.visibility = View.VISIBLE

            tvtitleLifestyle.setText(resources.getString(R.string.countries_visited))
            ivImageTitle.setImageResource(R.drawable.ic_international)
            drawerLayout.openDrawer(GravityCompat.END)
            Handler().postDelayed({

                getCountriesData()
            }, 1000)


            nestedscroll.scrollTo(0, 0);


        } else if (i == R.id.llPets) {

             llalllistview.visibility = View.GONE
           
            mShimmerViewContainer!!.visibility = View.VISIBLE
            mShimmerView2!!.visibility = View.GONE


            tvTitle1.visibility = View.GONE
            tvTitle2.visibility = View.GONE
            tvTitle3.visibility = View.GONE
            llSpotify.visibility = View.GONE

            hobbies_layout.visibility = View.VISIBLE
            fashion_layout.visibility = View.GONE
            rv_indoor.visibility = View.GONE
            rv_outdoor.visibility = View.GONE

            tvtitleLifestyle.setText(resources.getString(R.string.pets_care))
            ivImageTitle.setImageResource(R.drawable.ic_pets)
            drawerLayout.openDrawer(GravityCompat.END)

            Handler().postDelayed({

                getPets()
            }, 1000)



            nestedscroll.scrollTo(0, 0);


        } else if (i == R.id.lltvshows) {

             llalllistview.visibility = View.GONE
            mShimmerViewContainer!!.visibility = View.GONE
            mShimmerView2!!.visibility = View.VISIBLE


            tvTitle1.visibility = View.VISIBLE
            tvTitle2.visibility = View.VISIBLE
//            tvTitle3.visibility = View.VISIBLE
            llSpotify.visibility = View.GONE
//            rv_indoor.layoutManager = GridLayoutManager(this, 2)

            hobbies_layout.visibility = View.VISIBLE
            fashion_layout.visibility = View.GONE
            rv_indoor.visibility = View.VISIBLE
            rv_outdoor.visibility = View.GONE

            tvTitle1.setText(resources.getString(R.string.channel))
            tvTitle2.setText(resources.getString(R.string.genre))
//            tvTitle3.setText(resources.getString(R.string.regional))
            tvTitle3.visibility = View.GONE

            tvtitleLifestyle.setText(resources.getString(R.string.tvshows))
            ivImageTitle.setImageResource(R.drawable.ic_tv_show)
            drawerLayout.openDrawer(GravityCompat.END)

            Handler().postDelayed({

                getTvShowsData()
            }, 1000)
            nestedscroll.scrollTo(0, 0);


        } else if (i == R.id.tv_go_ahead) {


//            val intent = Intent(this@Lifestyle_Activity, SocialFilter_Personal::class.java)
//            startActivity(intent)
//            overridePendingTransition(R.anim.move_left_enter, R.anim.move_left_exit)
//            finish();


        } else if (i == R.id.ivBackLifstyle) {

            onBackPressed()
        } else if (i == R.id.tvLifestyleSubmit) {

            drawerLayout.closeDrawer(GravityCompat.END)
        }


    }


    private fun getHobies() {

        Constants.retrofitService.getHobiesInterest.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

                try {
                    val string = response.body()!!.string()

                    val jsonObject = JSONObject(string)
                    val jsonObjectInfo = jsonObject.getJSONObject("info")

                    val jsonarray_popular = jsonObjectInfo.getJSONArray("popular")
                    val jsonarray_indoor = jsonObjectInfo.getJSONArray("indoor")
                    val jsonarray_outdoor = jsonObjectInfo.getJSONArray("outdoor")
                    val popular_list = gson!!.fromJson<List<Hobbies>>(
                        jsonarray_popular.toString(),
                        object : TypeToken<List<Hobbies>>() {}.type
                    )
                    val indoor_list = gson!!.fromJson<List<Hobbies>>(
                        jsonarray_indoor.toString(),
                        object : TypeToken<List<Hobbies>>() {}.type
                    )
                    val outdoor_list = gson!!.fromJson<List<Hobbies>>(
                        jsonarray_outdoor.toString(),
                        object : TypeToken<List<Hobbies>>() {}.type
                    )



                    hobies_adapter = HobbiesAdapter(this@Lifestyle_Activity, popular_list)
                    indoor_adapter = HobbiesAdapter(this@Lifestyle_Activity, indoor_list)
                    outdoor_adapter = HobbiesAdapter(this@Lifestyle_Activity, outdoor_list)


                    navigation_rv.layoutManager = GridLayoutManager(this@Lifestyle_Activity, 3)
                    rv_indoor.layoutManager = GridLayoutManager(this@Lifestyle_Activity, 3)
                    rv_outdoor.layoutManager = GridLayoutManager(this@Lifestyle_Activity, 3)

                    navigation_rv.adapter = hobies_adapter
                    rv_indoor.adapter = indoor_adapter
                    rv_outdoor.adapter = outdoor_adapter

                    hobies_adapter.notifyDataSetChanged()
                    indoor_adapter.notifyDataSetChanged()
                    outdoor_adapter.notifyDataSetChanged()

//                    
                    mShimmerViewContainer!!.visibility = View.GONE
                    llalllistview.visibility = View.VISIBLE


                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(
                    "TAG",
                    "onFailure() called with: call = [" + call.request()
                        .url() + "], t = [" + t + "]",
                    t
                )


            }
        })

    }

    private fun getMusic() {

        Constants.retrofitService.getMusic.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

                try {

                    val string = response.body()!!.string()
                    val jsonObject = JSONObject(string)
                    val jsonObjectInfo = jsonObject.getJSONObject("info")

                    val jsonarray_popular = jsonObjectInfo.getJSONArray("genre")

                    val popular_list = gson!!.fromJson<List<Hobbies>>(
                        jsonarray_popular.toString(),
                        object : TypeToken<List<Hobbies>>() {}.type
                    )


                    tvTitle2.visibility = View.GONE
                    tvTitle3.visibility = View.GONE
                    rv_indoor.visibility = View.GONE
                    rv_outdoor.visibility = View.GONE

                    val popular_adapter = CusineAdapter(this@Lifestyle_Activity, popular_list)

                    navigation_rv.layoutManager = GridLayoutManager(this@Lifestyle_Activity, 2)

                    navigation_rv.adapter = popular_adapter
                    popular_adapter.notifyDataSetChanged()

                    mShimmerViewContainer!!.visibility = View.GONE
                    mShimmerView2!!.visibility = View.GONE
                    llalllistview.visibility = View.VISIBLE


                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(
                    "TAG",
                    "onFailure() called with: call = [" + call.request()
                        .url() + "], t = [" + t + "]",
                    t
                )


            }
        })

    }

    private fun getBooks() {

        Constants.retrofitService.getBooks.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

                try {
                    val string = response.body()!!.string()
                    val jsonObject = JSONObject(string)
                    val jsonObjectInfo = jsonObject.getJSONObject("info")

                    val jsonarray_popular = jsonObjectInfo.getJSONArray("popular")

                    val popular_list = gson!!.fromJson<List<Hobbies>>(
                        jsonarray_popular.toString(),
                        object : TypeToken<List<Hobbies>>() {}.type
                    )

                    tvTitle2.visibility = View.GONE
                    tvTitle3.visibility = View.GONE
                    rv_indoor.visibility = View.GONE
                    rv_outdoor.visibility = View.GONE



                    val popular_adapter = CusineAdapter(this@Lifestyle_Activity, popular_list)

                    navigation_rv.layoutManager = GridLayoutManager(this@Lifestyle_Activity, 2)
                    llalllistview.visibility = View.VISIBLE
                    navigation_rv.adapter = popular_adapter
                    popular_adapter.notifyDataSetChanged()

                    mShimmerViewContainer!!.visibility = View.GONE
                    mShimmerView2!!.visibility = View.GONE



                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(
                    "TAG",
                    "onFailure() called with: call = [" + call.request()
                        .url() + "], t = [" + t + "]",
                    t
                )


            }
        })

    }

    private fun getSportsData() {

        Constants.retrofitService.getSports.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

                try {

                    val string = response.body()!!.string()
                    val jsonObject = JSONObject(string)
                    val jsonObjectInfo = jsonObject.getJSONObject("info")

                    val jsonarray_popular = jsonObjectInfo.getJSONArray("popular")
                    val jsonarray_indoor = jsonObjectInfo.getJSONArray("indoor")
                    val jsonarray_outdoor = jsonObjectInfo.getJSONArray("outdoor")

                    val popular_list = gson!!.fromJson<List<Hobbies>>(
                        jsonarray_popular.toString(),
                        object : TypeToken<List<Hobbies>>() {}.type
                    )
                    val indoor_list = gson!!.fromJson<List<Hobbies>>(
                        jsonarray_indoor.toString(),
                        object : TypeToken<List<Hobbies>>() {}.type
                    )
                    val outdoor_list = gson!!.fromJson<List<Hobbies>>(
                        jsonarray_outdoor.toString(),
                        object : TypeToken<List<Hobbies>>() {}.type
                    )


                    hobies_adapter = HobbiesAdapter(this@Lifestyle_Activity, popular_list)
                    indoor_adapter = HobbiesAdapter(this@Lifestyle_Activity, indoor_list)
                    outdoor_adapter = HobbiesAdapter(this@Lifestyle_Activity, outdoor_list)


                    navigation_rv.layoutManager = GridLayoutManager(this@Lifestyle_Activity, 3)
                    rv_indoor.layoutManager = GridLayoutManager(this@Lifestyle_Activity, 3)
                    rv_outdoor.layoutManager = GridLayoutManager(this@Lifestyle_Activity, 3)


                    navigation_rv.adapter = hobies_adapter
                    rv_indoor.adapter = indoor_adapter
                    rv_outdoor.adapter = outdoor_adapter

                    hobies_adapter.notifyDataSetChanged()
                    indoor_adapter.notifyDataSetChanged()
                    outdoor_adapter.notifyDataSetChanged()

                    navigation_rv.visibility=View.VISIBLE
                    rv_indoor.visibility=View.VISIBLE
                    rv_outdoor.visibility=View.VISIBLE

                    
                    mShimmerViewContainer!!.visibility = View.GONE
                    llalllistview.visibility = View.VISIBLE


                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(
                    "TAG",
                    "onFailure() called with: call = [" + call.request()
                        .url() + "], t = [" + t + "]",
                    t
                )


            }
        })

    }

    private fun getFashionData() {

        Constants.retrofitService.getfashions.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

                try {

                    tvT1.setText(resources.getString(R.string.popular_brands))
                    tvT2.setText(resources.getString(R.string.premium))
                    tvT3.setText(resources.getString(R.string.casual))
                    tvT4.setText(resources.getString(R.string.sportswear))
                    tvT5.setText(resources.getString(R.string.designer))
                    tvT6.setText(resources.getString(R.string.official))
                    tvT7.setText(resources.getString(R.string.makeup))
                    tvT7.visibility = View.VISIBLE
                    rv_makeup.visibility = View.VISIBLE
                    val string = response.body()!!.string()
                    val jsonObject = JSONObject(string)
                    val jsonObjectInfo = jsonObject.getJSONObject("info")


                    val jsonarray_popular = jsonObjectInfo.getJSONArray("popular")
                    val jsonarray_premium = jsonObjectInfo.getJSONArray("premium")
                    val jsonarray_casual = jsonObjectInfo.getJSONArray("casual")
                    val jsonarray_sports = jsonObjectInfo.getJSONArray("sports")
                    val jsonarray_designer = jsonObjectInfo.getJSONArray("designer")
                    val jsonarray_offical = jsonObjectInfo.getJSONArray("official")
                    val jsonarray_makeup = jsonObjectInfo.getJSONArray("makeup")

                    val popular_list = gson!!.fromJson<List<Hobbies>>(
                        jsonarray_popular.toString(),
                        object : TypeToken<List<Hobbies>>() {}.type
                    )
                    val premium_list = gson!!.fromJson<List<Hobbies>>(
                        jsonarray_premium.toString(),
                        object : TypeToken<List<Hobbies>>() {}.type
                    )
                    val casual_list = gson!!.fromJson<List<Hobbies>>(
                        jsonarray_casual.toString(),
                        object : TypeToken<List<Hobbies>>() {}.type
                    )
                    val sports_list = gson!!.fromJson<List<Hobbies>>(
                        jsonarray_sports.toString(),
                        object : TypeToken<List<Hobbies>>() {}.type
                    )
                    val designer_list = gson!!.fromJson<List<Hobbies>>(
                        jsonarray_designer.toString(),
                        object : TypeToken<List<Hobbies>>() {}.type
                    )
                    val official_list = gson!!.fromJson<List<Hobbies>>(
                        jsonarray_offical.toString(),
                        object : TypeToken<List<Hobbies>>() {}.type
                    )
                    val makeup_list = gson!!.fromJson<List<Hobbies>>(
                        jsonarray_makeup.toString(),
                        object : TypeToken<List<Hobbies>>() {}.type
                    )



                    /*Fashion*/

                    rv_popular.layoutManager = GridLayoutManager(this@Lifestyle_Activity, 2)
                    rv_premium.layoutManager = GridLayoutManager(this@Lifestyle_Activity, 2)
                    rv_casual.layoutManager = GridLayoutManager(this@Lifestyle_Activity, 2)
                    rv_sports.layoutManager = GridLayoutManager(this@Lifestyle_Activity, 2)
                    rv_designer.layoutManager = GridLayoutManager(this@Lifestyle_Activity, 2)
                    rv_official.layoutManager = GridLayoutManager(this@Lifestyle_Activity, 2)
                    rv_makeup.layoutManager = GridLayoutManager(this@Lifestyle_Activity, 2)


                    val popular_adapter = FashionAdapter(this@Lifestyle_Activity, popular_list)
                    val prem_adapter = FashionAdapter(this@Lifestyle_Activity, premium_list)
                    val casual_adapter = FashionAdapter(this@Lifestyle_Activity, casual_list)
                    val sports_adapter = FashionAdapter(this@Lifestyle_Activity, sports_list)
                    val designer_adapter =
                        FashionAdapter(this@Lifestyle_Activity, designer_list)
                    val offcial_adapter =
                        FashionAdapter(this@Lifestyle_Activity, official_list)
                    val make_adapter = FashionAdapter(this@Lifestyle_Activity, makeup_list)



                    rv_popular.adapter = popular_adapter
                    rv_premium.adapter = prem_adapter
                    rv_casual.adapter = casual_adapter
                    rv_sports.adapter = sports_adapter
                    rv_designer.adapter = designer_adapter
                    rv_official.adapter = offcial_adapter
                    rv_makeup.adapter = make_adapter


                    popular_adapter.notifyDataSetChanged()
                    prem_adapter.notifyDataSetChanged()
                    casual_adapter.notifyDataSetChanged()
                    sports_adapter.notifyDataSetChanged()
                    designer_adapter.notifyDataSetChanged()
                    offcial_adapter.notifyDataSetChanged()
                    make_adapter.notifyDataSetChanged()

                    
                    mShimmerViewContainer!!.visibility = View.GONE
                    mShimmerView2!!.visibility = View.GONE

                    llalllistview.visibility = View.VISIBLE

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(
                    "TAG", "onFailure() called with: call = [" + call.request()
                        .url() + "], t = [" + t + "]", t
                )


            }
        })

    }

    private fun getCusinesData() {

        Constants.retrofitService.getcuisines.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

                try {

                    val string = response.body()!!.string()
                    val jsonObject = JSONObject(string)
                    val jsonObjectInfo = jsonObject.getJSONObject("info")

                    val jsonarray_popular = jsonObjectInfo.getJSONArray("popular")
                    val jsonarray_global = jsonObjectInfo.getJSONArray("global")
                    val jsonarray_regional = jsonObjectInfo.getJSONArray("regional")

                    val popular_list = gson!!.fromJson<List<Hobbies>>(
                        jsonarray_popular.toString(),
                        object : TypeToken<List<Hobbies>>() {}.type
                    )
                    val global_list = gson!!.fromJson<List<Hobbies>>(
                        jsonarray_global.toString(),
                        object : TypeToken<List<Hobbies>>() {}.type
                    )
                    val regional_list = gson!!.fromJson<List<Hobbies>>(
                        jsonarray_regional.toString(),
                        object : TypeToken<List<Hobbies>>() {}.type
                    )



                    val popular_adapter = CusineAdapter(this@Lifestyle_Activity, popular_list)
                    val global_adapter = CusineAdapter(this@Lifestyle_Activity, global_list)
                    val regional_adapter =
                        CusineAdapter(this@Lifestyle_Activity, regional_list)


                    navigation_rv.layoutManager = GridLayoutManager(this@Lifestyle_Activity, 2)
                    rv_indoor.layoutManager = GridLayoutManager(this@Lifestyle_Activity, 2)
                    rv_outdoor.layoutManager = GridLayoutManager(this@Lifestyle_Activity, 2)


                    navigation_rv.adapter = popular_adapter
                    rv_indoor.adapter = global_adapter
                    rv_outdoor.adapter = regional_adapter

                    popular_adapter.notifyDataSetChanged()
                    global_adapter.notifyDataSetChanged()
                    regional_adapter.notifyDataSetChanged()

                    
                    mShimmerViewContainer!!.visibility = View.GONE
                    mShimmerView2!!.visibility = View.GONE
                    llalllistview.visibility = View.VISIBLE

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(
                    "TAG",
                    "onFailure() called with: call = [" + call.request()
                        .url() + "], t = [" + t + "]",
                    t
                )


            }
        })

    }

    private fun getMovies() {

        Constants.retrofitService.getMovies.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

                try {

                    val string = response.body()!!.string()
                    val jsonObject = JSONObject(string)
                    val jsonObjectInfo = jsonObject.getJSONObject("info")

                    val jsonarray_popular = jsonObjectInfo.getJSONArray("popular")

                    val popular_list = gson!!.fromJson<List<Hobbies>>(
                        jsonarray_popular.toString(),
                        object : TypeToken<List<Hobbies>>() {}.type
                    )

                    tvTitle2.visibility = View.GONE
                    tvTitle3.visibility = View.GONE
                    rv_indoor.visibility = View.GONE
                    rv_outdoor.visibility = View.GONE



                    val popular_adapter = CusineAdapter(this@Lifestyle_Activity, popular_list)

                    navigation_rv.layoutManager = GridLayoutManager(this@Lifestyle_Activity, 2)

                    navigation_rv.adapter = popular_adapter
                    popular_adapter.notifyDataSetChanged()

                    llalllistview.visibility = View.VISIBLE
                    mShimmerViewContainer!!.visibility = View.GONE
                    mShimmerView2!!.visibility = View.GONE



                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(
                    "TAG",
                    "onFailure() called with: call = [" + call.request()
                        .url() + "], t = [" + t + "]",
                    t
                )


            }
        })

    }

    private fun getTvShowsData() {

        Constants.retrofitService.getShows.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

                try {
                    val string = response.body()!!.string()
                    val jsonObject = JSONObject(string)
                    val jsonObjectInfo = jsonObject.getJSONObject("info")

                    val jsonarray_channel = jsonObjectInfo.getJSONArray("channel")
                    val jsonarray_genre = jsonObjectInfo.getJSONArray("genre")
//                    val jsonarray_regional = jsonObjectInfo.getJSONArray("regional")

                    val popular_list = gson!!.fromJson<List<Hobbies>>(
                        jsonarray_channel.toString(),
                        object : TypeToken<List<Hobbies>>() {}.type
                    )
                    val genre_list = gson!!.fromJson<List<Hobbies>>(
                        jsonarray_genre.toString(),
                        object : TypeToken<List<Hobbies>>() {}.type
                    )

                    val popular_adapter = CusineAdapter(this@Lifestyle_Activity, popular_list)
                    val genre_adapter = CusineAdapter(this@Lifestyle_Activity, genre_list)


                    navigation_rv.layoutManager = GridLayoutManager(this@Lifestyle_Activity, 2)
                    rv_indoor.layoutManager = GridLayoutManager(this@Lifestyle_Activity, 2)
//                    rv_outdoor.layoutManager = GridLayoutManager(this@Lifestyle_Activity, 2)


                    navigation_rv.adapter = popular_adapter
                    rv_indoor.adapter = genre_adapter

                    popular_adapter.notifyDataSetChanged()
                    genre_adapter.notifyDataSetChanged()
                    
                    mShimmerViewContainer!!.visibility = View.GONE
                    mShimmerView2!!.visibility = View.GONE
                    llalllistview.visibility = View.VISIBLE


                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(
                    "TAG",
                    "onFailure() called with: call = [" + call.request()
                        .url() + "], t = [" + t + "]",
                    t
                )


            }
        })

    }

    private fun getPets() {

        Constants.retrofitService.getpets.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

                try {
                    val string = response.body()!!.string()
                    val jsonObject = JSONObject(string)
                    val jsonObjectInfo = jsonObject.getJSONObject("info")

                    val jsonarray_popular = jsonObjectInfo.getJSONArray("popular")

                    val popular_list = gson!!.fromJson<List<Hobbies>>(
                        jsonarray_popular.toString(),
                        object : TypeToken<List<Hobbies>>() {}.type
                    )



                    val popular_adapter = HobbiesAdapter(this@Lifestyle_Activity, popular_list)

                    navigation_rv.layoutManager = GridLayoutManager(this@Lifestyle_Activity, 3)

                    navigation_rv.adapter = popular_adapter

                    popular_adapter.notifyDataSetChanged()

                    
                    mShimmerViewContainer!!.visibility = View.GONE
                    llalllistview.visibility = View.VISIBLE


                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(
                    "TAG",
                    "onFailure() called with: call = [" + call.request()
                        .url() + "], t = [" + t + "]",
                    t
                )


            }
        })

    }

    private fun getCountriesData() {

        Constants.retrofitService.getcountries.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

                try {
                    tvT1.setText(resources.getString(R.string.asia))
                    tvT2.setText(resources.getString(R.string.europe))
                    tvT3.setText(resources.getString(R.string.n_america))
                    tvT4.setText(resources.getString(R.string.s_america))
                    tvT5.setText(resources.getString(R.string.australia))
                    tvT6.setText(resources.getString(R.string.africa))
                    tvT7.visibility = View.GONE
                    rv_makeup.visibility = View.GONE

                    val string = response.body()!!.string()
                    val jsonObject = JSONObject(string)
                    val jsonObjectInfo = jsonObject.getJSONObject("info")

                    val jsonarray_asia = jsonObjectInfo.getJSONArray("asia")
                    val jsonarray_europe = jsonObjectInfo.getJSONArray("europe")
                    val jsonarray_n_america = jsonObjectInfo.getJSONArray("northAmerica")
                    val jsonarray_s_america = jsonObjectInfo.getJSONArray("southAmerica")
                    val jsonarray_australia = jsonObjectInfo.getJSONArray("australia")
                    val jsonarray_africa = jsonObjectInfo.getJSONArray("africa")

                    val asia_list = gson!!.fromJson<List<Hobbies>>(
                        jsonarray_asia.toString(),
                        object : TypeToken<List<Hobbies>>() {}.type
                    )
                    val europe_list = gson!!.fromJson<List<Hobbies>>(
                        jsonarray_europe.toString(),
                        object : TypeToken<List<Hobbies>>() {}.type
                    )
                    val n_amer_list = gson!!.fromJson<List<Hobbies>>(
                        jsonarray_n_america.toString(),
                        object : TypeToken<List<Hobbies>>() {}.type
                    )

                    val s_amer_list = gson!!.fromJson<List<Hobbies>>(
                        jsonarray_s_america.toString(),
                        object : TypeToken<List<Hobbies>>() {}.type
                    )
                    val australi_list = gson!!.fromJson<List<Hobbies>>(
                        jsonarray_australia.toString(),
                        object : TypeToken<List<Hobbies>>() {}.type
                    )
                    val africa_list = gson!!.fromJson<List<Hobbies>>(
                        jsonarray_africa.toString(),
                        object : TypeToken<List<Hobbies>>() {}.type
                    )




                    val asia_adapter = HobbiesAdapter(this@Lifestyle_Activity, asia_list)
                    val euro_adapter = HobbiesAdapter(this@Lifestyle_Activity, europe_list)
                    val n_amera_adapter = HobbiesAdapter(this@Lifestyle_Activity, n_amer_list)
                    val s_amera_adapter = HobbiesAdapter(this@Lifestyle_Activity, s_amer_list)
                    val austr_adapter = HobbiesAdapter(this@Lifestyle_Activity, australi_list)
                    val africa_adapter = HobbiesAdapter(this@Lifestyle_Activity, africa_list)


                    rv_popular.layoutManager = GridLayoutManager(this@Lifestyle_Activity, 3)
                    rv_premium.layoutManager = GridLayoutManager(this@Lifestyle_Activity, 3)
                    rv_casual.layoutManager = GridLayoutManager(this@Lifestyle_Activity, 3)
                    rv_sports.layoutManager = GridLayoutManager(this@Lifestyle_Activity, 3)
                    rv_designer.layoutManager = GridLayoutManager(this@Lifestyle_Activity, 3)
                    rv_official.layoutManager = GridLayoutManager(this@Lifestyle_Activity, 3)



                    rv_popular.adapter = asia_adapter
                    rv_premium.adapter = euro_adapter
                    rv_casual.adapter = n_amera_adapter
                    rv_sports.adapter = s_amera_adapter
                    rv_designer.adapter = austr_adapter
                    rv_official.adapter = africa_adapter
//                    rv_makeup.adapter = make_adapter


                    asia_adapter.notifyDataSetChanged()
                    euro_adapter.notifyDataSetChanged()
                    n_amera_adapter.notifyDataSetChanged()
                    s_amera_adapter.notifyDataSetChanged()
                    austr_adapter.notifyDataSetChanged()
                    africa_adapter.notifyDataSetChanged()

                    
                    mShimmerViewContainer!!.visibility = View.GONE
                    llalllistview.visibility = View.VISIBLE

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(
                    "TAG",
                    "onFailure() called with: call = [" + call.request()
                        .url() + "], t = [" + t + "]",
                    t
                )


            }
        })

    }



    override fun onBackPressed() {


        val i = Intent(this@Lifestyle_Activity, PersonalDetailsActivity::class.java)
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
}