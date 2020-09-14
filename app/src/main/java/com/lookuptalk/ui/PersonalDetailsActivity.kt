package com.lookuptalk.ui

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSpinner
import com.bumptech.glide.Glide
import com.github.clans.fab.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.lb.utils.Constants
import com.lb.utils.UserSession
import com.lookuptalk.LoginResponse
import com.lookuptalk.R
import com.lookuptalk.adapter.FlagListAdapter
import com.lookuptalk.customfonts.EditeText_font
import com.lookuptalk.customfonts.Ferrara_Bold
import com.lookuptalk.customfonts.MyTextView_Bold
import com.lookuptalk.customfonts.MyTextView_Normal
import com.lookuptalk.helper.SelectableRoundedImageView
import com.lookuptalk.model.ModelFlag
import kotlinx.android.synthetic.main.professional_content.*
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList


class PersonalDetailsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var llSocial: LinearLayout
    private lateinit var llProfessional: LinearLayout
    private lateinit var tv_fill_linkedin: MyTextView_Normal
    private lateinit var tv_fill_facebook: MyTextView_Normal


    lateinit var task_dict_data: JSONObject

    private var mProfileUrl: String = ""

    private val selectedMediaGridColumnCount = 5
    private val allSelectedMediaPaths by lazy { arrayListOf<String>() }

    private lateinit var gsonObject: JsonObject

    /*Personal Data*/
    private var mName: String = ""
    private var mAge: String = ""
    private var mSex: String = ""
    private var mMaritalStatus: String = ""
    private var mSexialOrientation: String = ""
    private var mLocation: String = ""
    private var mQualification: String = ""
    private var mHeight: String = ""
    private var mBodyType: String = ""
    private var mOccupation: String = ""
    private var mSmoke: Boolean = false
    private var mDrink: Boolean = false
    private var mAboutYourself: String = ""

    /*Widgets*/
    private lateinit var tvScreenName: Ferrara_Bold
    private lateinit var ivProfilePic: SelectableRoundedImageView
    private lateinit var fab_camera: ImageView
    private lateinit var tv_FillMore: MyTextView_Normal
    private lateinit var etName: EditeText_font
    private lateinit var etAge: EditeText_font
    private lateinit var sp_marital: Spinner
    private lateinit var sp_sex: Spinner
    private lateinit var sp_sexoreint: Spinner
    private lateinit var tvLocation: MyTextView_Normal
    private lateinit var sp_Qualification: Spinner
    private lateinit var sp_height: Spinner
    private lateinit var sp_bodytype: Spinner
    private lateinit var sp_occupation: Spinner
    private lateinit var sp_keyskills: Spinner
    private lateinit var sp_industry: Spinner
    private lateinit var switch_smoke: Switch
    private lateinit var switch_drink: Switch
    private lateinit var etAboutmyself: EditeText_font


    private var gson: Gson? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.personalprofile)

        gson = Gson()
        init()

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            lytRoot.systemUiVisibility =
//                lytRoot.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//        }

        // Get Device Width in Px


        getOccupations()
        getHeights()
        getSkills()
        getIndustry()

        val data = UserSession(this@PersonalDetailsActivity).getGoogleData()
        task_dict_data = JSONObject()


        if (!data.equals("")) {
            try {
                task_dict_data = JSONObject(data)

                mProfileUrl = task_dict_data.optString("googleProfilePicURL")
                mName = task_dict_data.optString("google_name")

            } catch (e: JSONException) {
                e.printStackTrace()
            }

            Log.e("Google_Plus***", "Name****  " + "\n" + mName)

        }

        val fb_data = UserSession(this@PersonalDetailsActivity).getFBData()
        task_dict_data = JSONObject()


        if (!fb_data.equals("")) {
            try {
                task_dict_data = JSONObject(fb_data)

                mProfileUrl = task_dict_data.optString("fb_proPic")
                mName = task_dict_data.optString("fb_proName")

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }


        etName.setText(mName)
        Glide.with(this@PersonalDetailsActivity).load(mProfileUrl)
            .into(ivProfilePic);


        sp_marital.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(arg0: AdapterView<*>, view: View?, arg2: Int, arg3: Long) {


                if (arg2 >= 0) {
                    mMaritalStatus = arg0.getItemAtPosition(arg2).toString()
                }

            }

            override fun onNothingSelected(arg0: AdapterView<*>) {
                // TODO Auto-generated method stub

            }
        }
        sp_sex.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(arg0: AdapterView<*>, view: View?, arg2: Int, arg3: Long) {

                if (arg2 >= 0) {
                    mSex = arg0.getItemAtPosition(arg2).toString()
                }

            }

            override fun onNothingSelected(arg0: AdapterView<*>) {
                // TODO Auto-generated method stub

            }
        }
        sp_sexoreint.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(arg0: AdapterView<*>, view: View?, arg2: Int, arg3: Long) {

                if (arg2 >= 0) {
                    mSexialOrientation = arg0.getItemAtPosition(arg2).toString()
                }

            }

            override fun onNothingSelected(arg0: AdapterView<*>) {
                // TODO Auto-generated method stub

            }
        }
        sp_Qualification.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(arg0: AdapterView<*>, view: View?, arg2: Int, arg3: Long) {

                if (arg2 >= 0) {
                    mQualification = arg0.getItemAtPosition(arg2).toString()
                }
            }

            override fun onNothingSelected(arg0: AdapterView<*>) {
                // TODO Auto-generated method stub

            }
        }
        sp_height.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(arg0: AdapterView<*>, view: View?, arg2: Int, arg3: Long) {

                if (arg2 >= 0) {
                    mHeight = arg0.getItemAtPosition(arg2).toString()
                }

            }

            override fun onNothingSelected(arg0: AdapterView<*>) {
                // TODO Auto-generated method stub

            }
        }
        sp_bodytype.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(arg0: AdapterView<*>, view: View?, arg2: Int, arg3: Long) {

                if (arg2 >= 0) {
                    mBodyType = arg0.getItemAtPosition(arg2).toString()
                }

            }

            override fun onNothingSelected(arg0: AdapterView<*>) {
                // TODO Auto-generated method stub

            }
        }
        sp_occupation.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(arg0: AdapterView<*>, view: View?, arg2: Int, arg3: Long) {
                if (arg2 >= 0) {
                    mOccupation = arg0.getItemAtPosition(arg2).toString()
                    Log.e("TAG","selected_occu "+ mOccupation)

                }


            }

            override fun onNothingSelected(arg0: AdapterView<*>) {
                // TODO Auto-generated method stub

            }
        }



        switch_smoke.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mSmoke = true
                // The toggle is enabled
            } else {

                mSmoke = false
                // The toggle is disabled
            }
        })

        switch_drink.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mDrink = true
                // The toggle is enabled
            } else {

                mDrink = false
                // The toggle is disabled
            }
        })


//        google_id_textview.text = googleId
//        google_first_name_textview.text = googleFirstName
//        google_last_name_textview.text = googleLastName
//        google_email_textview.text = googleEmail
//        google_profile_pic_textview.text = googleProfilePicURL
    }


    private fun init() {

//        val typeface = Typeface.createFromAsset(assets, "fonts/BOOKOS.TTF")

        // Set the typeface
//        txtMessage.typeface = typeface

        tvScreenName = findViewById(R.id.tvScreenName)
        ivProfilePic = findViewById(R.id.tvProfileImage)
        fab_camera = findViewById(R.id.fab_camera)
        etName = findViewById(R.id.etName)
        sp_sex = findViewById(R.id.sp_sex)
        etAge = findViewById(R.id.etAge)
        sp_marital = findViewById(R.id.sp_marital)
        sp_sexoreint = findViewById(R.id.sp_sexoreint)
        tvLocation = findViewById(R.id.tvLocation)
        sp_Qualification = findViewById(R.id.sp_Qualification)
        sp_height = findViewById(R.id.sp_height)
        sp_bodytype = findViewById(R.id.sp_bodytype)
        sp_occupation = findViewById(R.id.sp_occupation)
        sp_keyskills = findViewById(R.id.sp_keyskills)
        sp_industry = findViewById(R.id.sp_Industries)
        switch_smoke = findViewById(R.id.switch_smoke)
        switch_drink = findViewById(R.id.switch_drink)
        etAboutmyself = findViewById(R.id.etAboutmyself)
//        llProfessional = findViewById(R.id.llProfessional)
//        tv_fill_facebook = findViewById(R.id.tv_fill_facebook)
//        tv_fill_linkedin = findViewById(R.id.tv_fill_linkedin)

        tvScreenName.setText(resources.getString(R.string.personal_details))

    }

    override fun onClick(v: View) {

        val i = v.id
        if (i == R.id.tv_FillMore) {

            mName = etName.text.toString()
            mAge = etAge.text.toString()
            mAboutYourself = etAboutmyself.text.toString()
            mLocation = tvLocation.text.toString()


            gsonObject = JsonObject()
            try {
                val login_json = JSONObject()
                login_json.put("fullName", mName)
                login_json.put("sex", mSex)
                login_json.put("age", mAge)
                login_json.put("maritalStatus", mMaritalStatus)
                login_json.put("sexualOrientation", mSexialOrientation)
                login_json.put("location", mLocation)
                login_json.put("qualification", mQualification)
                login_json.put("height", mHeight)
                login_json.put("bodyType", mBodyType)
                login_json.put("occupation", mOccupation)
                login_json.put("smoke", mSmoke)
                login_json.put("drink", mDrink)
                login_json.put("description", mAboutYourself)

                val jsonParser = JsonParser()
                gsonObject = jsonParser.parse(login_json.toString()) as JsonObject
//            updatepersonalData(gsonObject)
//                    startActivity(Intent(this@LoginActivity, Verfication_Activity::class.java))
//                    overridePendingTransition(R.anim.move_left_enter, R.anim.move_left_exit)
//                    finish()

            } catch (e: JSONException) {
                e.printStackTrace()
            }

            val intent = Intent(this@PersonalDetailsActivity, Lifestyle_Activity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.move_left_enter, R.anim.move_left_exit)
            finish();
        }else if(i==R.id.fab_camera){

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


    private fun updatepersonalData(gsonObject: JsonObject) {
        val mProgressDialog = ProgressDialog(this@PersonalDetailsActivity)
        mProgressDialog.isIndeterminate = true
        mProgressDialog.setMessage("Loading...")
        mProgressDialog.show()


        val call: Call<LoginResponse> = Constants.retrofitService.updatePersonal(gsonObject)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>, response: Response<LoginResponse>
            ) {

                val string = response.body()!!.toString()
                if (response.body()!!.description.equals("Otp Sent Successfully")) {

                }


                //hiding progress dialog
                mProgressDialog.dismiss()

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                mProgressDialog.dismiss()
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getOccupations()
      {
        val mProgressDialog = ProgressDialog(this@PersonalDetailsActivity)
        mProgressDialog.isIndeterminate = true
        mProgressDialog.setMessage("Loading...")
        mProgressDialog.show()
        Constants.retrofitService.getOccupations.enqueue(object : Callback<ResponseBody> {
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

                    sp_occupation.adapter = ArrayAdapter(this@PersonalDetailsActivity,android.R.layout.simple_spinner_dropdown_item, nameslist)

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
    private fun getHeights() {
        val mProgressDialog = ProgressDialog(this@PersonalDetailsActivity)
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

                    sp_height.adapter = ArrayAdapter(this@PersonalDetailsActivity,android.R.layout.simple_spinner_dropdown_item, nameslist)

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
        val mProgressDialog = ProgressDialog(this@PersonalDetailsActivity)
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

                    sp_keyskills.adapter = ArrayAdapter(this@PersonalDetailsActivity,android.R.layout.simple_spinner_dropdown_item, nameslist)

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
        val mProgressDialog = ProgressDialog(this@PersonalDetailsActivity)
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

                    sp_industry.adapter = ArrayAdapter(this@PersonalDetailsActivity,android.R.layout.simple_spinner_dropdown_item, nameslist)

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

            Glide.with(this@PersonalDetailsActivity).load(data?.data)
                .into(ivProfilePic);
        }
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }



    override fun onBackPressed() {

        val i = Intent(this@PersonalDetailsActivity, SelectProfileActivity::class.java)
        startActivity(i)
        overridePendingTransition(R.anim.move_left_enter, R.anim.move_left_exit)
        finish();
    }
}