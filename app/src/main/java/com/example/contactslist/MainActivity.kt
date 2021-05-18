package com.example.contactslist

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val isTablet = resources.getBoolean(R.bool.isTablet)
        if(!isTablet) requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        supportFragmentManager.apply {
            val listContactsFragment = ListContactsFragment.newInstance()
            val transaction = beginTransaction()

            transaction.replace(R.id.frame_layout, listContactsFragment)
            transaction.commit()
        }
    }
}