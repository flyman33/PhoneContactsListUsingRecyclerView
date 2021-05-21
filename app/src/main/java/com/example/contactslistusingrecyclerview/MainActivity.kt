package com.example.contactslistusingrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    companion object {
        var isOld = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.apply {
            val listContactsFragment = ListContactsFragment.newInstance()
            val transaction = beginTransaction()

            transaction.replace(R.id.frame_layout, listContactsFragment)
            transaction.commit()
        }
    }
}