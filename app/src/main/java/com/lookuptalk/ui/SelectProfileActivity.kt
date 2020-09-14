package com.lookuptalk.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.BuildConfig
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.lb.utils.UserSession
import com.lookuptalk.R
import com.lookuptalk.customfonts.MyTextView_Normal
import org.json.JSONException
import org.json.JSONObject

class SelectProfileActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var llSocial: LinearLayout
    private lateinit var llProfessional: LinearLayout
    private lateinit var tv_fillbyyourself: MyTextView_Normal
    private lateinit var tv_fillby_fb: MyTextView_Normal
    private lateinit var tvsocialtext: MyTextView_Normal
    private lateinit var tvprofessionaltext: MyTextView_Normal

    var checked_value: Int = 0

    /*Facebook*/
    lateinit var callbackManager: CallbackManager

    var id = ""
    var firstName = ""
    var middleName = ""
    var lastName = ""
    var name = ""
    var picture = ""
    var email = ""
    var accessToken = ""
    lateinit var fb_data: JSONObject;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_selection)

        init()

        /*Facebook*/
        callbackManager = CallbackManager.Factory.create()

    }


    private fun init() {
        llSocial = findViewById(R.id.llSocial)
        llProfessional = findViewById(R.id.llProfessional)
        tvsocialtext = findViewById(R.id.tvsocialtext)
        tvprofessionaltext = findViewById(R.id.tvprofessionaltext)
        llProfessional = findViewById(R.id.llProfessional)
        tv_fillbyyourself = findViewById(R.id.tv_fillbyyourself)
        tv_fillby_fb = findViewById(R.id.tv_fillby_fb)

    }

    override fun onClick(v: View) {

        val i = v.id
        if (i == R.id.llSocial) {

            checked_value = 1

            llSocial.setBackgroundColor(resources.getColor(R.color.light_blue_1))
            tvsocialtext.setBackgroundColor(resources.getColor(R.color.blue))
            tvsocialtext.setTextColor(resources.getColor(R.color.white))

            llProfessional.setBackgroundColor(resources.getColor(R.color.white))
            tvprofessionaltext.setBackgroundColor(resources.getColor(R.color.white_gray))
            tvprofessionaltext.setTextColor(resources.getColor(R.color.gray))

            tv_fillby_fb.visibility = View.VISIBLE
            tv_fillby_fb.setText(resources.getString(R.string.fill_fb))
            tv_fillbyyourself.visibility = View.VISIBLE

//            val i = Intent(this@SelectProfileActivity, PersonalDetailsActivity::class.java)
//            startActivity(i)
//            overridePendingTransition(R.anim.move_left_enter, R.anim.move_left_exit)
//            finish();

        } else if (i == R.id.llProfessional) {

            checked_value = 2

            llProfessional.setBackgroundColor(resources.getColor(R.color.light_blue_1))
            tvprofessionaltext.setBackgroundColor(resources.getColor(R.color.blue))
            tvprofessionaltext.setTextColor(resources.getColor(R.color.white))

            llSocial.setBackgroundColor(resources.getColor(R.color.white))
            tvsocialtext.setBackgroundColor(resources.getColor(R.color.white_gray))
            tvsocialtext.setTextColor(resources.getColor(R.color.gray))

            tv_fillby_fb.visibility = View.VISIBLE
            tv_fillby_fb.setText(resources.getString(R.string.fill_likdin))
            tv_fillbyyourself.visibility = View.VISIBLE

        } else if (i == R.id.tv_fillby_fb) {


            if (tv_fillby_fb.text.toString().equals(resources.getString(R.string.fill_fb))) {

                LoginManager.getInstance()
                    .logInWithReadPermissions(this, listOf("public_profile", "email"))
                // Callback registration
                LoginManager.getInstance()
                    .registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
                        override fun onSuccess(loginResult: LoginResult?) {
                            Log.d("TAG", "Success Login")
                            getUserProfile(
                                loginResult?.accessToken,
                                loginResult?.accessToken?.userId
                            )
                        }

                        override fun onCancel() {
                            Toast.makeText(
                                this@SelectProfileActivity,
                                "Login Cancelled",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        override fun onError(exception: FacebookException) {
                            Toast.makeText(
                                this@SelectProfileActivity,
                                exception.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })

            } else {

            }


        } else if (i == R.id.tv_fillbyyourself) {

            if (checked_value.equals(1)) {
                val i = Intent(this@SelectProfileActivity, PersonalDetailsActivity::class.java)
                startActivity(i)
                overridePendingTransition(R.anim.move_left_enter, R.anim.move_left_exit)
                finish();
            } else {
                val i = Intent(this@SelectProfileActivity, ProfessionalDetails_Activity::class.java)
                startActivity(i)
                overridePendingTransition(R.anim.move_left_enter, R.anim.move_left_exit)
                finish();
            }


        }
    }


    @SuppressLint("LongLogTag")
    fun getUserProfile(token: AccessToken?, userId: String?) {

        val parameters = Bundle()
        parameters.putString(
            "fields", "id, first_name, middle_name, last_name, name, picture, email")

//        loginButton.setReadPermissions("email", "public_profile", "user_friends");

        GraphRequest(token,
            "/$userId/",
            parameters,
            HttpMethod.GET,
            GraphRequest.Callback { response ->
                val jsonObject = response.jsonObject

                // Facebook Access Token
                // You can see Access Token only in Debug mode.
                // You can't see it in Logcat using Log.d, Facebook did that to avoid leaking user's access token.
                if (BuildConfig.DEBUG) {
                    FacebookSdk.setIsDebugEnabled(true)
                    FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS)
                }
                accessToken = token.toString()

                // Facebook Id
                if (jsonObject.has("id")) {
                    val facebookId = jsonObject.getString("id")
                    Log.i("Facebook Id: ", facebookId.toString())
                    id = facebookId.toString()
                } else {
                    Log.i("Facebook Id: ", "Not exists")
                    id = "Not exists"
                }


                // Facebook First Name
                if (jsonObject.has("first_name")) {
                    val facebookFirstName = jsonObject.getString("first_name")
                    Log.i("Facebook First Name: ", facebookFirstName)
                    firstName = facebookFirstName
                } else {
                    Log.i("Facebook First Name: ", "Not exists")
                    firstName = "Not exists"
                }


                // Facebook Middle Name
                if (jsonObject.has("middle_name")) {
                    val facebookMiddleName = jsonObject.getString("middle_name")
                    Log.i("Facebook Middle Name: ", facebookMiddleName)
                    middleName = facebookMiddleName
                } else {
                    Log.i("Facebook Middle Name: ", "Not exists")
                    middleName = "Not exists"
                }


                // Facebook Last Name
                if (jsonObject.has("last_name")) {
                    val facebookLastName = jsonObject.getString("last_name")
                    Log.i("Facebook Last Name: ", facebookLastName)
                    lastName = facebookLastName
                } else {
                    Log.i("Facebook Last Name: ", "Not exists")
                    lastName = "Not exists"
                }


                // Facebook Name
                if (jsonObject.has("name")) {
                    val facebookName = jsonObject.getString("name")
                    Log.i("Facebook Name: ", facebookName)
                    name = facebookName
                } else {
                    Log.i("Facebook Name: ", "Not exists")
                    name = "Not exists"
                }


                // Facebook Profile Pic URL
                if (jsonObject.has("picture")) {
                    val facebookPictureObject = jsonObject.getJSONObject("picture")
                    if (facebookPictureObject.has("data")) {
                        val facebookDataObject = facebookPictureObject.getJSONObject("data")
                        if (facebookDataObject.has("url")) {
                            val facebookProfilePicURL = facebookDataObject.getString("url")
                            Log.i("Facebook Profile Pic URL: ", facebookProfilePicURL)
                            picture = facebookProfilePicURL
                        }
                    }
                } else {
                    Log.i("Facebook Profile Pic URL: ", "Not exists")
                    picture = "Not exists"
                }

                // Facebook Email
                if (jsonObject.has("email")) {
                    val facebookEmail = jsonObject.getString("email")
                    Log.i("Facebook Email: ", facebookEmail)
                    email = facebookEmail
                } else {
                    Log.i("Facebook Email: ", "Not exists")
                    email = "Not exists"
                }

                openDetailsActivity()
            }).executeAsync()
    }

    fun isLoggedIn(): Boolean {
        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired
        return isLoggedIn
    }


    fun logOutUser() {
        LoginManager.getInstance().logOut()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun openDetailsActivity() {

        fb_data = JSONObject()
        try {
            val fb_pic = "https://graph.facebook.com/" + id + "/picture?type=large"

            fb_data.put("fb_proName", name);
            fb_data.put("fb_proEmail", email);
            fb_data.put("fb_proPic", fb_pic);
        } catch (e: JSONException) {
            e.printStackTrace();
        }
        UserSession(this@SelectProfileActivity).setFBData(fb_data.toString())
        startActivity(Intent(this@SelectProfileActivity, PersonalDetailsActivity::class.java))
        overridePendingTransition(R.anim.move_left_enter, R.anim.move_left_exit)
        finish()
//        val myIntent = Intent(this, DetailsActivity::class.java)
//        myIntent.putExtra("facebook_id", id)
//        myIntent.putExtra("facebook_first_name", firstName)
//        myIntent.putExtra("facebook_middle_name", middleName)
//        myIntent.putExtra("facebook_last_name", lastName)
//        myIntent.putExtra("facebook_name", name)
//        myIntent.putExtra("facebook_picture", picture)
//        myIntent.putExtra("facebook_email", email)
//        myIntent.putExtra("facebook_access_token", accessToken)
//        startActivity(myIntent)
    }
}