package com.lookuptalk.ui

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.github.clans.fab.FloatingActionButton
import com.lb.utils.Constants
import com.lookuptalk.R
import com.lookuptalk.customfonts.Ferrara_Bold
import com.lookuptalk.customfonts.MyTextView_Bold
import com.lookuptalk.customfonts.MyTextView_Normal
import com.lookuptalk.helper.SelectableRoundedImageView
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class ProfessionalDetails_Activity : AppCompatActivity(),View.OnClickListener{


    private lateinit var ivprofe_ProfileImage: SelectableRoundedImageView
    private lateinit var prof_fab_camera: FloatingActionButton
    private lateinit var tvScreenName: Ferrara_Bold
    private lateinit var sp_designation: Spinner
    private lateinit var sp_industry: Spinner
    private lateinit var sp_experience: Spinner
    private lateinit var sp_qualificaiton: Spinner
    private lateinit var sp_keyskills: Spinner
    private lateinit var sp_Industries_interested: Spinner



    private lateinit var tv_prof_FillMore: MyTextView_Normal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.professional_profile)

        init()
        getDesignations()
        getKeyskills()
        getIndustry()
    }


    private fun init() {

//        val typeface = Typeface.createFromAsset(assets, "fonts/BOOKOS.TTF")

        // Set the typeface
//        txtMessage.typeface = typeface

        ivprofe_ProfileImage = findViewById(R.id.ivprofe_ProfileImage)
        prof_fab_camera = findViewById(R.id.prof_fab_camera)
        tvScreenName = findViewById(R.id.tvScreenName)
        sp_designation = findViewById(R.id.sp_designation)
        sp_industry = findViewById(R.id.sp_industry)
        sp_experience = findViewById(R.id.sp_experience)
        sp_qualificaiton = findViewById(R.id.sp_qualificaiton)
        sp_keyskills = findViewById(R.id.sp_keyskills)
        sp_Industries_interested = findViewById(R.id.sp_Industries_interested)

        tv_prof_FillMore = findViewById(R.id.tv_prof_FillMore)
        tvScreenName.setText(resources.getString(R.string.personal_details))

    }

    override fun onClick(v: View) {

        val i = v.id
        if (i == R.id.tv_prof_FillMore) {


            val intent = Intent(this@ProfessionalDetails_Activity, SocialFilter_Professional::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.move_left_enter, R.anim.move_left_exit)
            finish();
        }else if(i==R.id.prof_fab_camera){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    //permission already granted
                    pickImageFromGallery();
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }


    }

    private fun getDesignations() {
        val mProgressDialog = ProgressDialog(this@ProfessionalDetails_Activity)
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

                    sp_designation.adapter = ArrayAdapter(this@ProfessionalDetails_Activity,
                        android.R.layout.simple_spinner_dropdown_item, nameslist)

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

    private fun getKeyskills() {
        val mProgressDialog = ProgressDialog(this@ProfessionalDetails_Activity)
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

                    sp_keyskills.adapter = ArrayAdapter(this@ProfessionalDetails_Activity,
                        android.R.layout.simple_spinner_dropdown_item, nameslist)

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
        val mProgressDialog = ProgressDialog(this@ProfessionalDetails_Activity)
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

                    sp_industry.adapter = ArrayAdapter(this@ProfessionalDetails_Activity,android.R.layout.simple_spinner_dropdown_item, nameslist)

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

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
//            ivProfilePic.setImageURI(data?.data)

            Glide.with(this@ProfessionalDetails_Activity).load(data?.data)
                .into(ivprofe_ProfileImage);
        }
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }


    override fun onBackPressed() {

        val i = Intent(this@ProfessionalDetails_Activity, SelectProfileActivity::class.java)
        startActivity(i)
        overridePendingTransition(R.anim.move_left_enter, R.anim.move_left_exit)
        finish();
    }
}