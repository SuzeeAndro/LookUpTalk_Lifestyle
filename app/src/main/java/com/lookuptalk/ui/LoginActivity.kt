package com.lookuptalk.ui

import android.app.ProgressDialog
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.hbb20.CountryCodePicker
import com.lb.utils.Constants
import com.lb.utils.UserSession
import com.lookuptalk.LoginResponse
import com.lookuptalk.MainActivity
import com.lookuptalk.R
import com.lookuptalk.customfonts.MyTextView_Normal
import com.lookuptalk.helper.SmsBroadcastReceiver
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), CountryCodePicker.OnCountryChangeListener,
    View.OnClickListener {


    /*Country Code*/
    private var ccp: CountryCodePicker? = null
    private var countryCode: String? = null
    private var countryName: String? = null
    private var mPhoneNumber: String = ""
    private lateinit var etMobile: EditText
    private lateinit var tv_OTP: MyTextView_Normal

    private lateinit var llverification: LinearLayout
    private lateinit var gsonObject: JsonObject
    lateinit var google_data: JSONObject;

    /*Google Plus*/
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001

    private var googleEmail: String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.login_layout)



        /*Google LOgin*/
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("933942339278-3tfsc58inkmd4aehadjo4k2gsegvrs61.apps.googleusercontent.com")
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)



        init()

        ccp = findViewById(R.id.country_code_picker)
        ccp!!.setOnCountryChangeListener(this)

        //to set default country code as Japan
        ccp!!.setDefaultCountryUsingNameCode("IN")

//        getPhoneNumberByTelephony()


    }



    private fun init() {
        etMobile = findViewById(R.id.etMobile)
        tv_OTP = findViewById(R.id.tv_OTP)

//        val colorStateList: ColorStateList = ColorStateList.valueOf(resources.getColor(R.color.line_gray))
//        ViewCompat.setBackgroundTintList(etMobile, colorStateList)

//        etMobile.addTextChangedListener(object : TextWatcher {
//
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
//
//            override fun afterTextChanged(textVal: Editable) {
//                mPhoneNumber = textVal.toString()
//                if (mPhoneNumber.length == 10) {
//
//
//
//                } else {
//
//                }
//            }
//        })

    }

    override fun onCountrySelected() {
        countryCode = ccp!!.selectedCountryCode
        countryName = ccp!!.selectedCountryName

//        Toast.makeText(this,"Country Code "+countryCode,Toast.LENGTH_SHORT).show()
//        Toast.makeText(this,"Country Name "+countryName,Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View) {

        val i = v.id
        if (i == R.id.tv_OTP) {

            if (etMobile.text.toString().length > 0) {

                gsonObject = JsonObject()
                try {
                    val login_json = JSONObject()
                    login_json.put("mobileNo", etMobile.text.toString().trim())
                    login_json.put("isGmail", false)
                    login_json.put("email", "")
                    login_json.put("otp", "")
                    login_json.put("reSend", false)

                    val jsonParser = JsonParser()
                    gsonObject = jsonParser.parse(login_json.toString()) as JsonObject
                    getLogin(gsonObject)
//                    startActivity(Intent(this@LoginActivity, Verfication_Activity::class.java))
//                    overridePendingTransition(R.anim.move_left_enter, R.anim.move_left_exit)
//                    finish()

                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            } else {
                Snackbar.make(v, "Invalid Number", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }


        } else if (i == R.id.tv_googlelogin) {
            signIn()
        }
    }


//    private fun getPhoneNumberByTelephony() {
//        try {
//            val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//            val imei = tm.deviceId
//            val tel = tm.line1Number
//            if (tel != null) {
//                Log.d("TAG", "MobileNumber By Telephony: $tel")
//                setMobileNumber(tel)
//            }
//
//
//        } catch (e: SecurityException) {
//            getPhoneNumberByGoogleAPI()
//        }
//    }

//    private fun getPhoneNumberByGoogleAPI() {
//        val hintRequest = HintRequest.Builder().setPhoneNumberIdentifierSupported(true).build()
//        try {
//            val googleApiClient =
//                GoogleApiClient.Builder(this@LoginActivity).addApi(Auth.CREDENTIALS_API).build()
//            val pendingIntent =
//                Auth.CredentialsApi.getHintPickerIntent(googleApiClient, hintRequest)
//            startIntentSenderForResult(pendingIntent.intentSender, PHONE_NUMBER_HINT, null, 0, 0, 0)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Log.e(TAG, "Could not start hint picker Intent", e);
//        }
//    }

    private fun getLogin(gsonObject: JsonObject) {
        val mProgressDialog = ProgressDialog(this@LoginActivity)
        mProgressDialog.isIndeterminate = true
        mProgressDialog.setMessage("Loading...")
        mProgressDialog.show()


        val call: Call<LoginResponse> = Constants.retrofitService.getlogin(gsonObject)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>, response: Response<LoginResponse>
            ) {

                val string = response.body()!!.toString()
                if (response.body()!!.description.equals("Otp Sent Successfully")) {

                    Toast.makeText(this@LoginActivity, "Otp Sent Successfully", Toast.LENGTH_SHORT)
                        .show()
                    val i = Intent(this@LoginActivity, Verfication_Activity::class.java)
                    i.putExtra("mobile", etMobile.text.toString().trim())
                    startActivity(i)
                    overridePendingTransition(R.anim.move_left_enter, R.anim.move_left_exit)

                    finish()
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

    /*Google {Plus Login*/

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(
            signInIntent, RC_SIGN_IN
        )
    }

    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {
                // Update your UI here
            }
    }

    private fun revokeAccess() {
        mGoogleSignInClient.revokeAccess()
            .addOnCompleteListener(this) {
                // Update your UI here
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(
                ApiException::class.java
            )
            // Signed in successfully
            val googleId = account?.id ?: ""
            Log.i("Google ID", googleId)

            val googleFirstName = account?.givenName ?: ""
            Log.i("Google First Name", googleFirstName)

            val googleLastName = account?.familyName ?: ""
            Log.i("Google Last Name", googleLastName)

            googleEmail = account?.email ?: ""
            Log.i("Google Email", googleEmail)

            val googleProfilePicURL = account?.photoUrl.toString()
            Log.i("Google Profile Pic URL", googleProfilePicURL)

            val googleIdToken = account?.idToken ?: ""
            Log.i("Google ID Token", googleIdToken)


            gsonObject = JsonObject()
            try {
                val login_json = JSONObject()
                login_json.put("mobileNo", "")
                login_json.put("isGmail", true)
                login_json.put("email", googleEmail)
                login_json.put("otp", "")
                login_json.put("reSend", false)

                val jsonParser = JsonParser()
                gsonObject = jsonParser.parse(login_json.toString()) as JsonObject

            } catch (e: JSONException) {
                e.printStackTrace()
            }

            google_data = JSONObject()
            try {

                google_data.put("google_name", googleFirstName + " " + googleLastName);
                google_data.put("google_Email", googleEmail);
                google_data.put("googleProfilePicURL", googleProfilePicURL);
            } catch (e: JSONException) {
                e.printStackTrace();
            }
            UserSession(this@LoginActivity).setGoogleData(google_data.toString())

            verifyGoogleLogin(gsonObject, googleEmail)

        } catch (e: ApiException) {
            // Sign in was unsuccessful
            Log.e(
                "failed code=", e.statusCode.toString()
            )
        }
    }


    private fun verifyGoogleLogin(
        gsonObject: JsonObject,
        googleEmail: String
    ) {
        val mProgressDialog = ProgressDialog(this@LoginActivity)
        mProgressDialog.isIndeterminate = true
        mProgressDialog.setMessage("Loading...")
        mProgressDialog.show()


        val call: Call<LoginResponse> = Constants.retrofitService.getlogin(gsonObject)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>, response: Response<LoginResponse>
            ) {

                val string = response.body()!!.toString()
                if (response.body()!!.description.equals("SUCCESS")) {

                    UserSession(this@LoginActivity).setGoogleLogin(googleEmail)
//                    Toast.makeText(this@LoginActivity, "Otp Verified", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, SelectProfileActivity::class.java))
                    overridePendingTransition(R.anim.move_left_enter, R.anim.move_left_exit)
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Invalid", Toast.LENGTH_SHORT).show()
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


}
