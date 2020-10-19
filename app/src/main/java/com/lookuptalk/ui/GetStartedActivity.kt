package com.lookuptalk.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.lookuptalk.utils.UserSession
import com.lookuptalk.R
import com.lookuptalk.customfonts.MyTextView_Normal

class GetStartedActivity  : AppCompatActivity(), View.OnClickListener {

    internal var mGetStarted: String = ""
    internal var mMobileNumber: String = ""
    internal var mEmailId: String = ""
    private lateinit var tvGetStarted: MyTextView_Normal
    private lateinit var llsplashlayout: LinearLayout


    private val SPLASH_TIME_OUT: Long = 1500 // 3 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.getstarted_layout)

        /*Get Mobile */
        mGetStarted = UserSession(this@GetStartedActivity).getGetStarted()
        mMobileNumber = UserSession(this@GetStartedActivity).getPhoneNum()
        mEmailId = UserSession(this@GetStartedActivity).getGoogleLogin()

        tvGetStarted = findViewById(R.id.tvGetStarted)


    }

    override fun onClick(v: View) {

        val i = v.id
        if (i == R.id.tvGetStarted) {

            UserSession(this@GetStartedActivity).setGetStarted("clicked")
            if (mEmailId.length > 0) {
                if (mEmailId.length > 0) {

                    val i = Intent(this@GetStartedActivity, SelectProfileActivity::class.java)
                    startActivity(i)
                    overridePendingTransition(R.anim.move_left_enter, R.anim.move_left_exit)
                    finish()
                } else {
                    val i = Intent(this@GetStartedActivity, LoginActivity::class.java)
                    startActivity(i)
                    overridePendingTransition(R.anim.move_left_enter, R.anim.move_left_exit)
                    finish();

                }

            } else {
                if (mMobileNumber.length > 0) {

                    val i = Intent(this@GetStartedActivity, SelectProfileActivity::class.java)
                    startActivity(i)
                    overridePendingTransition(R.anim.move_left_enter, R.anim.move_left_exit)
                    finish()
                } else {
                    val i = Intent(this@GetStartedActivity, LoginActivity::class.java)
                    startActivity(i)
                    overridePendingTransition(R.anim.move_left_enter, R.anim.move_left_exit)
                    finish();
                }
            }

        }

    }

}
