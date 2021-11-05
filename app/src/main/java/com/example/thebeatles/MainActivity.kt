package com.example.thebeatles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.*

class MainActivity : AppCompatActivity() {
  var albums = arrayListOf<Album>()
  var stopThread = false  // To notify if the thread in webView needs to be stopped

  companion object {
    private var instance: MainActivity? = null

    public fun getInstance(): MainActivity {
      return instance!!
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    instance = this
    supportActionBar?.elevation = 0F
    setContentView(R.layout.activity_main)


    var bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
    val navController = Navigation.findNavController(this, R.id.fragment)
    bottomNavigation.setupWithNavController(navController)

    // Get the InputStream associated with a given file
    var is1 = this.assets.open("albums.txt")

    // Convert to a BufferedReader
    var reader1 = BufferedReader(InputStreamReader(is1))

    // Read all the lines in the file and store in an array list
    var lines1 = reader1.readLines()

    // Convert the List to an array of Line
    var arrayLines1 = lines1.toTypedArray()

    // Store the fields as a 2-D Array of Strings (Parse the ^ delimited fields)
    for (element in arrayLines1) {
      var array1 = element.split("^")
      var album = Album(array1[0], array1[1], array1[2], array1[3])
      albums.add(album)
    }

    //Close the Reader
    reader1.close()
  }
}
