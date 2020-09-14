package com.lookuptalk.ui

import android.R
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
import com.lookuptalk.customfonts.MyTextView_Bold
import com.mohammedalaa.seekbar.DoubleValueSeekBarView
import com.mohammedalaa.seekbar.OnDoubleValueSeekBarChangeListener
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SocialFilter_Professional : AppCompatActivity(), View.OnClickListener {

    lateinit var tvScreenName: MyTextView_Bold
    lateinit var backtoolbar: RelativeLayout
    lateinit var sp_professIndistry: Spinner
    lateinit var sp_profess_skills: Spinner
    lateinit var sp_profess_designation: Spinner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.lookuptalk.R.layout.socialfilters_proffesional)

        inti()

        getDesignation()
        getIndustry()
        getSkills()
    }

    private fun inti() {

        sp_professIndistry = findViewById(com.lookuptalk.R.id.sp_professIndistry)
        sp_profess_designation = findViewById(com.lookuptalk.R.id.sp_profess_designation)
        sp_profess_skills = findViewById(com.lookuptalk.R.id.sp_profess_skills)
        tvScreenName = findViewById(com.lookuptalk.R.id.tvScreenName)
        backtoolbar = findViewById(com.lookuptalk.R.id.backtoolbar)
        tvScreenName.setText(resources.getString(com.lookuptalk.R.string.professional_filter))


    }

    override fun onClick(v: View) {

        val i = v.id
        if (i == com.lookuptalk.R.id.backtoolbar) {
            onBackPressed()

        }

    }

    private fun getDesignation() {
        val mProgressDialog = ProgressDialog(this@SocialFilter_Professional)
        mProgressDialog.isIndeterminate = true
        mProgressDialog.setMessage("Loading...")
        mProgressDialog.show()
        Constants.retrofitService.getDesignations.enqueue(object : Callback<ResponseBody> {
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

                    sp_profess_designation.adapter = ArrayAdapter(this@SocialFilter_Professional,android.R.layout.simple_spinner_dropdown_item, nameslist)

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
        val mProgressDialog = ProgressDialog(this@SocialFilter_Professional)
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

                    sp_professIndistry.adapter = ArrayAdapter(this@SocialFilter_Professional,android.R.layout.simple_spinner_dropdown_item, nameslist)

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
    private fun getSkills() {
        val mProgressDialog = ProgressDialog(this@SocialFilter_Professional)
        mProgressDialog.isIndeterminate = true
        mProgressDialog.setMessage("Loading...")
        mProgressDialog.show()
        Constants.retrofitService.getskills.enqueue(object : Callback<ResponseBody> {
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

                    sp_profess_skills.adapter = ArrayAdapter(this@SocialFilter_Professional,android.R.layout.simple_spinner_dropdown_item, nameslist)

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

        val i = Intent(this@SocialFilter_Professional, ProfessionalDetails_Activity::class.java)
        startActivity(i)
        overridePendingTransition(com.lookuptalk.R.anim.move_left_enter, com.lookuptalk.R.anim.move_left_exit)
        finish();
    }
}