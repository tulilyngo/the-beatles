package com.example.thebeatles

import android.widget.*
import android.content.*
import java.util.*


class SlideShow : Thread {
  private var duration: Long = 0

  var stopThread = false

  constructor(duration: Long) {
    this.duration = duration
  }

  override public fun run() {
    var count = 0
    var files = arrayOf(
      "intro1",
      "abbeyroad",
      "beatlesforsale",
      "harddaysnight",
      "help",
      "letitbe",
      "magicalmysterytour",
      "pastmastersvolume1",
      "pastmastersvolume2",
      "pleasepleaseme",
      "revolver",
      "rubber_soul",
      "sgt_pepper",
      "white",
      "with_the_beatles",
      "yellowsubmarine"
    )
    var captions = arrayOf(
      "The Beatles",
      "Abbey Road",
      "Beatles For Sale",
      "A Hard Day’s Night",
      "HELP!",
      "Let It Be",
      "Magical Mystery Tour",
      "Past Masters Volume I",
      "Past Masters Volume II",
      "Please Please Me",
      "Revolver",
      "Rubber Soul",
      "Sgt. Pepper",
      "The White Album",
      "With The Beatles",
      "Yellow Submarine"
    )
    // Check if other fragment menu was clicked
    while (!MainFragment.getInstance().slideShow.stopThread) {
      Thread.sleep(duration * 1000) //Delay
      var handler = HandlerThread(files[count % files.size], captions[count % files.size])
      MainActivity.getInstance().runOnUiThread(handler)
      count++
    }
  }

}

class HandlerThread : Runnable {
  private var fn: String = ""
  private var caption: String = ""


  constructor(fn: String, caption: String) {
    this.fn = fn
    this.caption = caption
  }

  override fun run() {
    if (!MainFragment.getInstance().slideShow.stopThread) {
      var imageView = MainActivity.getInstance().findViewById<ImageView>(R.id.cover)
      var textView = MainActivity.getInstance().findViewById<TextView>(R.id.textView)
      textView.setText(caption)

      var id = MainActivity.getInstance().resources.getIdentifier(
        fn,
        "drawable",
        MainActivity.getInstance().packageName
      )
      imageView.setImageResource(id)
    } else {
      return
    }
  }
}
