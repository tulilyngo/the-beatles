package com.example.thebeatles

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.InputStreamReader
import android.view.View.*
import android.webkit.WebView
import android.webkit.WebViewClient
import org.json.JSONObject
import java.net.URL
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.appcompat.app.AlertDialog
import android.content.DialogInterface.*
import android.content.DialogInterface.*
import android.os.Build

import android.webkit.PermissionRequest

import android.webkit.WebChromeClient

class RecyclerAdapterSong : RecyclerView.Adapter<RecyclerAdapterSong.ViewHolder> {
  private var pos: Int = -1
  var songs = arrayListOf<Song>()

  constructor(pos: Int) : this() {
    this.pos = pos

    // Get the album file to read the songs from
    var album = MainActivity.getInstance().albums[pos].getFile() + ".txt"

    // Get the InputStream associated with a given file
    var is1 = MainActivity.getInstance().assets.open(album)

    // Convert to a BufferedReader
    var reader1 = BufferedReader(InputStreamReader(is1))

    // Read all the lines in the file and store in an array list
    var lines1 = reader1.readLines()

    // Convert the List to an array of Line
    var arrayLines1 = lines1.toTypedArray()

    // Store the fields as a 2-D Array of Strings (Parse the ^ delimited fields)
    for (element in arrayLines1) {
      var array1 = element.split("^")
      var song = Song(array1[0], array1[1])
      songs.add(song)
    }

    //Close the Reader
    reader1.close()
  }

  constructor() {
  }

  public fun setPos(pos: Int) {
    this.pos = pos
  }

  override fun onBindViewHolder(holder: RecyclerAdapterSong.ViewHolder, position: Int) {
    holder.itemSong.text = songs[position].getSong()
    holder.itemProducer.text = songs[position].getProducer()
  }

  override fun getItemCount(): Int {
    return songs.size
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): RecyclerAdapterSong.ViewHolder {
    val v =
      LayoutInflater.from(parent.context).inflate(R.layout.song_card_layout, parent, false)
    return ViewHolder(v)
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var itemSong: TextView
    var itemProducer: TextView
    var handler = Handler()

    init {
      itemSong = itemView.findViewById(R.id.song)
      itemProducer = itemView.findViewById(R.id.producer)
      itemView.setOnClickListener(handler)
    }

    inner class Handler : View.OnClickListener {
      override fun onClick(v: View?) {
        val itemPos = layoutPosition

        // Set the song
        var song = songs[itemPos].getSong()
        var origSong = song

        // Song titles with apostrophes are returned by YouTube API with "&#39;"
        if (song.contains("\'")) {
          origSong = origSong.replace("\'", "&#39;")
        }
        song = song.replace(" ", "+")

        // Set the artist
        var artist = "The Beatles"
        var origArtist = artist
        artist = artist.replace(" ", "+")

        // Encode search for YouTube
        val keywords = artist + "+" + song
        val max = 50

        // Set the youtube search
        val url =
          "https://www.googleapis.com/youtube/v3/search?part=snippet&q=$keywords&order=viewCount&maxResults=$max&type=video&videoCategory=Music&key=AIzaSyC8q2VhbVX4370rj_Bz6CQefflpBAchXDE"

        var helper = Helper(url, origSong, origArtist)
        var thread = Thread(helper)
        thread.start()
      }
    }
  }
}

class Helper : Runnable {
  private var url: String = ""
  private var song: String = ""
  private var artist: String = ""

  constructor(url: String, song: String, artist: String) {
    this.url = url
    this.song = song
    this.artist = artist
  }

  override fun run() {
    if (!MainActivity.getInstance().stopThread) {
      // Read all the data at this URL
      val data = URL(url).readText()

      // Read the data as text
      var json = JSONObject(data)

      // Extract the array of items
      var items = json.getJSONArray("items") //this is the "items": [ ] part

      // Create arrays for titles and videos
      var titles = ArrayList<String>()
      var videos = ArrayList<String>()

      // Loop through all items extracting out the title/video id
      for (i in 0 until items.length()) {
        // Get the ith item
        var videoObject = items.getJSONObject(i)

        // Extract the id Hashmap
        var idDict = videoObject.getJSONObject("id")

        // Get the videoid using videoid key
        var videoId = idDict.getString("videoId")

        // Get the snippet Hashmap
        var snippetDict = videoObject.getJSONObject("snippet")

        // Get the title
        var title = snippetDict.getString("title")

        // Add the titles to the lists
        titles.add(title)
        videos.add(videoId)
      }

      // Select the best matching video: has "The Beatles" and song's name in the video title
      var selected_video = ""
      for (title in titles) {
        if (title.contains(artist) && title.contains(song)) {
          selected_video = videos.elementAt(titles.indexOf(title))
          break
        }
      }
      // If there is a best matching video, play it on YouTube
      if (selected_video.isNotEmpty()) {
        var helper1 = UIThreadHelper(selected_video)
        MainActivity.getInstance().runOnUiThread(helper1)
      } else {
        // Notify user that no best matching video can be found
        MainActivity.getInstance().stopThread = true
        var helper2 = Helper2()
        MainActivity.getInstance().runOnUiThread(helper2)
      }
    } else {
      return
    }
  }
}

class Helper2 : Runnable {
  override fun run() {
    val dialogBuilder = AlertDialog.Builder(SongFragment.getInstance().requireContext())

    var handler = Handler1()

    // Set the message and button
    dialogBuilder.setMessage("Best matching video not found on YouTube")
    dialogBuilder.setPositiveButton("OK", handler)

    // Create the alert box
    val alert = dialogBuilder.create()

    // Set the title
    alert.setTitle("Error")

    // Show the alert box
    alert.show()
  }

  inner class Handler1 : DialogInterface.OnClickListener {
    override fun onClick(dialog: DialogInterface?, which: Int) {
      MainActivity.getInstance().stopThread = false
    }
  }
}

class UIThreadHelper : Runnable {
  private var video: String = ""

  constructor(video: String) {
    this.video = video
  }

  override fun run() {
    // Update the webView
    var web = MainActivity.getInstance().findViewById<WebView>(R.id.webView)
    val settings = web.getSettings()
    settings.setJavaScriptEnabled(true)
    settings.setDomStorageEnabled(true)
    settings.setMinimumFontSize(10)
    settings.setLoadWithOverviewMode(true)
    settings.setUseWideViewPort(true)
    settings.setBuiltInZoomControls(true)
    settings.setDisplayZoomControls(false)
    web.setVerticalScrollBarEnabled(false)
    settings.setDomStorageEnabled(true)
    web.setWebChromeClient(WebChromeClient())

    var str = "https://www.youtube.com/watch?v=$video"
    web.loadUrl(str)
  }
}
