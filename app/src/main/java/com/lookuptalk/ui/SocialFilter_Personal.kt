package com.lookuptalk.ui

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RelativeLayout
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.lb.utils.Constants
import com.lookuptalk.R
import com.lookuptalk.customfonts.Ferrara_Bold
import com.lookuptalk.customfonts.MyTextView_Bold
import com.lookuptalk.customfonts.MyTextView_Normal
import com.mohammedalaa.seekbar.DoubleValueSeekBarView
import com.mohammedalaa.seekbar.OnDoubleValueSeekBarChangeListener
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SocialFilter_Personal : AppCompatActivity(), View.OnClickListener {

    lateinit var doubleValueSeekBarView: DoubleValueSeekBarView
    lateinit var tvScreenName: Ferrara_Bold
    lateinit var tvAgeRange: MyTextView_Bold
    lateinit var tv_filterProceed: MyTextView_Normal
    lateinit var backtoolbar: RelativeLayout
    lateinit var sp_filter_height: Spinner
    lateinit var sp_filter_industry: Spinner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.socialfilters)

        inti()

        getHeights()
        getIndustry()
    }

    private fun inti() {
        tvAgeRange = findViewById(R.id.tvAgeRange)
        sp_filter_height = findViewById(R.id.sp_filter_height)
        sp_filter_industry = findViewById(R.id.sp_filter_industry)
        tv_filterProceed = findViewById(R.id.tv_filterProceed)
        tvScreenName = findViewById(R.id.tvScreenName)
        backtoolbar = findViewById(R.id.backtoolbar)
        tvScreenName.setText(resources.getString(R.string.social_filter))
        doubleValueSeekBarView = findViewById(R.id.double_range_seekbar)
        doubleValueSeekBarView.currentMinValue = 20
        doubleValueSeekBarView.minStep = 10

        doubleValueSeekBarView.currentMaxValue = 90
//        doubleValueSeekBarView.maxStep=20

        doubleValueSeekBarView.setOnRangeSeekBarViewChangeListener(object :
            OnDoubleValueSeekBarChangeListener {
            override fun onValueChanged(
                seekBar: DoubleValueSeekBarView?,
                min: Int,
                max: Int,
                fromUser: Boolean
            ) {
                Log.e("onChanged->", "$min - $max")

                tvAgeRange.setText("$min - $max")

            }

            override fun onStartTrackingTouch(
                seekBar: DoubleValueSeekBarView?,
                min: Int,
                max: Int
            ) {
                Log.e("onStart->", "Min $min : Max $max")
            }

            override fun onStopTrackingTouch(seekBar: DoubleValueSeekBarView?, min: Int, max: Int) {
                Log.e("onStop->", "Min $min : Max $max")
            }

        })

    }

    override fun onClick(v: View) {

        val i = v.id
        if (i == R.id.backtoolbar) {
            onBackPressed()

        }else if (i == R.id.tv_filterProceed) {
//            val intent = Intent(this@SocialFilter_Personal, Lifestyle_Activity::class.java)
//            startActivity(intent)
//            overridePendingTransition(R.anim.move_left_enter, R.anim.move_left_exit)
//            finish();

        }

    }

    private fun getHeights() {
        val mProgressDialog = ProgressDialog(this@SocialFilter_Personal)
        mProgressDialog.isIndeterminate = true
        mProgressDialog.setMessage("Loading...")
        mProgressDialog.show()
        Constants.retrofitService.getHeights.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

                try {
                    val string = response.body()!!.string()

                    val jsonObject = JSONObject(string)
                    val jsonarray = jsonObject.getJSONArray("info")

                    val nameslist: ArrayList<String> = ArrayList()

                    for (i in 0 until jsonarray.length()) {
                        val item = jsonarray.getJSONObject(i).get("name")
                        Log.e("TAG", "List " + item)
                        nameslist.add(item as String)
                    }
                    Log.e("TAG", "occuaptions***" + nameslist)

                    sp_filter_height.adapter = ArrayAdapter(this@SocialFilter_Personal,android.R.layout.simple_spinner_dropdown_item, nameslist)

                    mProgressDialog.dismiss()


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

                if (mProgressDialog.isShowing)
                    mProgressDialog.dismiss()
            }
        })

    }
    private fun getIndustry() {
        val mProgressDialog = ProgressDialog(this@SocialFilter_Personal)
        mProgressDialog.isIndeterminate = true
        mProgressDialog.setMessage("Loading...")
        mProgressDialog.show()
        Constants.retrofitService.getIndustry.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {

                try {
                    val string = response.body()!!.string()

                    val jsonObject = JSONObject(string)
                    val jsonarray = jsonObject.getJSONArray("info")

                    val nameslist: ArrayList<String> = ArrayList()

                    for (i in 0 until jsonarray.length()) {
                        val item = jsonarray.getJSONObject(i).get("name")
                        Log.e("TAG", "List " + item)
                        nameslist.add(item as String)
                    }
                    Log.e("TAG", "occuaptions***" + nameslist)

                    sp_filter_industry.adapter = ArrayAdapter(this@SocialFilter_Personal,android.R.layout.simple_spinner_dropdown_item, nameslist)

                    mProgressDialog.dismiss()


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

                if (mProgressDialog.isShowing)
                    mProgressDialog.dismiss()
            }
        })

    }
    override fun onBackPressed() {

        val i = Intent(this@SocialFilter_Personal, PersonalDetailsActivity::class.java)
        startActivity(i)
        overridePendingTransition(R.anim.move_left_enter, R.anim.move_left_exit)
        finish();
    }
}