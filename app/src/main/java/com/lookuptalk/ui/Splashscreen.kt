package com.lookuptalk.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.lb.utils.UserSession
import com.lookuptalk.R


class Splashscreen : AppCompatActivity() {

    internal var mGetStarted: String = ""
    internal var mMobileNumber: String = ""
    internal var mEmailId: String = ""


    private val SPLASH_TIME_OUT: Long = 1500 // 3 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)

        /*Get Mobile */
        mGetStarted = UserSession(this@Splashscreen).getGetStarted()
        mMobileNumber = UserSession(this@Splashscreen).getPhoneNum()
        mEmailId = UserSession(this@Splashscreen).getGoogleLogin()


        if (mGetStarted.equals("")) {
            Handler().postDelayed({

                val i = Intent(this@Splashscreen, GetStartedActivity::class.java)
                startActivity(i)
                overridePendingTransition(R.anim.move_left_enter, R.anim.move_left_exit)
                finish()
            }, SPLASH_TIME_OUT)
        }else{
            Handler().postDelayed({
                if (mMobileNumber.length > 0) {
                    if (mMobileNumber.length > 0) {

                        val i = Intent(this@Splashscreen, SelectProfileActivity::class.java)
                        startActivity(i)
                        overridePendingTransition(R.anim.move_left_enter, R.anim.move_left_exit)
                        finish()
                    } else {
                        val i = Intent(this@Splashscreen, LoginActivity::class.java)
                        startActivity(i)
                        overridePendingTransition(R.anim.move_left_enter, R.anim.move_left_exit)
                        finish();

                    }

                } else {
                    if (mMobileNumber.length > 0) {

                        val i = Intent(this@Splashscreen, SelectProfileActivity::class.java)
                        startActivity(i)
                        overridePendingTransition(R.anim.move_left_enter, R.anim.move_left_exit)
                        finish()
                    } else {
                        val i = Intent(this@Splashscreen, LoginActivity::class.java)
                        startActivity(i)
                        overridePendingTransition(R.anim.move_left_enter, R.anim.move_left_exit)
                        finish();

                    }
                }

            }, SPLASH_TIME_OUT)
        }

    }


}
