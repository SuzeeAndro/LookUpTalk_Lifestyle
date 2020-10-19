package com.lookuptalk.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.lookuptalk.R
import com.lookuptalk.fragment.GridFragment
import com.lookuptalk.fragment.ProfileFragment
import com.lookuptalk.fragment.ChatFragment
import com.lookuptalk.fragment.HomeFragment
import kotlinx.android.synthetic.main.homescreen.*

class HomeScreen : AppCompatActivity() {

    private val chatFragment = ChatFragment()
    private val profileFragment = ProfileFragment()
    private val gridFragment = GridFragment()
    private val homeFragment = HomeFragment()
    private val fragmentManager = supportFragmentManager
    private var activeFragment: Fragment = homeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homescreen)
        fragmentManager.beginTransaction().apply {
            add(R.id.container, chatFragment, getString(R.string.chat)).hide(chatFragment)
            add(R.id.container, profileFragment, getString(R.string.profile)).hide(profileFragment)
            add(R.id.container, gridFragment, getString(R.string.grid)).hide(gridFragment)
            add(R.id.container, homeFragment, getString(R.string.home))
        }.commit()
        initListeners()
        bottomNavigationView.itemIconTintList = null
    }

    private fun initListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit()
                    activeFragment = homeFragment
                    true
                }

                R.id.navigation_profile -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(profileFragment).commit()
                    activeFragment = profileFragment
                    true
                }

                R.id.navigation_grid -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(gridFragment).commit()
                    activeFragment = gridFragment
                    true
                }
                R.id.navigation_chat -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(chatFragment).commit()
                    activeFragment = chatFragment
                    true
                }

                else -> false
            }
        }
    }
}
