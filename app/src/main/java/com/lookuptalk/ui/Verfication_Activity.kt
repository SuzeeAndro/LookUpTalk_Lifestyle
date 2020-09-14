package com.lookuptalk.ui

import `in`.aabhasjindal.otptextview.OTPListener
import `in`.aabhasjindal.otptextview.OtpTextView
import android.app.ProgressDialog
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.gson.JsonObject
import com.google.gson.JsonParser
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


class Verfication_Activity : AppCompatActivity(),
    View.OnClickListener {


    /*Country Code*/
    private lateinit var tv_Verify: MyTextView_Normal
    private lateinit var tvResendOTP: MyTextView_Normal
    private lateinit var pinview_OTP: OtpTextView
    private lateinit var ivBackbutton: ImageView

    private var mOtp: String = ""
    private var mMobile: String = ""
    private lateinit var gsonObject: JsonObject

    /*Retrieve OTP*/
    private val REQ_USER_CONSENT: Int = 200
    lateinit var smsBroadcastReceiver: SmsBroadcastReceiver
    var message: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.login_verification)

        startSmsUserConsent()

        mMobile = intent.getStringExtra("mobile")

        init()

//        startSMSListener()


    }

    private fun init() {
        tv_Verify = findViewById(R.id.tv_Verify)
        tvResendOTP = findViewById(R.id.tvResendOTP)
        pinview_OTP = findViewById(R.id.otp_view)
        ivBackbutton = findViewById(R.id.ivBackbutton)

        pinview_OTP.requestFocusOTP()
        pinview_OTP.otpListener = object : OTPListener {
            override fun onInteractionListener() {

            }

            override fun onOTPComplete(otp: String) {
                mOtp = otp


            }
        }


    }

    override fun onClick(v: View) {

        val i = v.id
        if (i == R.id.tv_Verify) {

            gsonObject = JsonObject()
            try {
                val login_json = JSONObject()
                login_json.put("mobileNo", mMobile)
                login_json.put("isGmail", false)
                login_json.put("email", "")
                login_json.put("otp", mOtp.toInt())
                login_json.put("reSend", false)

                val jsonParser = JsonParser()
                gsonObject = jsonParser.parse(login_json.toString()) as JsonObject
                verifyLogin(gsonObject)
            } catch (e: JSONException) {
                e.printStackTrace()
            }


        } else if (i == R.id.ivBackbutton) {

            onBackPressed()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()

        val i = Intent(this@Verfication_Activity, LoginActivity::class.java)
        startActivity(i)
        finish();
    }

    private fun verifyLogin(gsonObject: JsonObject) {
        val mProgressDialog = ProgressDialog(this@Verfication_Activity)
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

                    UserSession(this@Verfication_Activity).setPhoneNum(mMobile)
                    Toast.makeText(this@Verfication_Activity, "Otp Verified", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(
                        Intent(this@Verfication_Activity, SelectProfileActivity::class.java)
                    )
                    overridePendingTransition(R.anim.move_left_enter, R.anim.move_left_exit)
                    finish()
                } else {
                    Toast.makeText(this@Verfication_Activity, "Invalid OTP", Toast.LENGTH_SHORT)
                        .show()
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

    /*Read SMS*/


    /*Retrieve OTP*/
    private fun startSmsUserConsent() {

        val client = SmsRetriever.getClient(this)
        //We can add sender phone number or leave it blank
        // I'm adding null here
        //We can add sender phone number or leave it blank
        // I'm adding null here
        client.startSmsUserConsent(null).addOnSuccessListener {
            Toast.makeText(applicationContext, "On Success", Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            Toast.makeText(applicationContext, "On OnFailure", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_USER_CONSENT) {
            if (resultCode == RESULT_OK && data != null) {
                //That gives all message to us.
                // We need to get the code from inside with regex
                message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
//                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
//                textViewMessage.setText(
//                    String.format(
//                        "%s - %s",
//                        getString(R.string.received_message),
//                        message
//                    )
//                )

                val arr = message.split(" ".toRegex(), 2).toTypedArray()
                val firstWord = arr[0]
                pinview_OTP.setOTP(firstWord)
//                mOtp = firstWord


//        getOtpFromMessage(message);
            }
        }
    }

    private fun registerBroadcastReceiver() {
        smsBroadcastReceiver = SmsBroadcastReceiver()
        smsBroadcastReceiver.smsBroadcastReceiverListener = object : SmsBroadcastReceiver.SmsBroadcastReceiverListener {
                override fun onSuccess(intent: Intent?) {
                    startActivityForResult(intent, REQ_USER_CONSENT)
                }

            override fun onFailure() {}
            }
        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        registerReceiver(smsBroadcastReceiver, intentFilter)
    }


    override fun onStart() {
        super.onStart()
        registerBroadcastReceiver()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(smsBroadcastReceiver)
    }

}
