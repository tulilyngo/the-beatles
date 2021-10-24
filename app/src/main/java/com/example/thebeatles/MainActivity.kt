package com.example.thebeatles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
  companion object {
    private var instance: MainActivity? = null

    public fun getInstance(): MainActivity {
      return instance!!
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    instance = this // have to move here
    setContentView(R.layout.activity_main)

    var bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

    val navController = Navigation.findNavController(this, R.id.fragment)
    bottomNavigation.setupWithNavController(navController)
  }
}
