package com.sathish.carmap.view

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.sathish.carmap.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

}