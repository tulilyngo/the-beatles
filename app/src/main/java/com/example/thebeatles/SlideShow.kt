package com.example.thebeatles

import android.widget.*
import android.content.*
import java.util.*

class SlideShow : Thread {
  private var noSlides = 0
  private var duration: Long = 0
  private var count: Int = 1
  private var imageView: ImageView? = null
  private var textView: TextView? = null

  constructor(duration: Long, noSlides: Int) {
    this.duration = duration
    this.noSlides = noSlides
    imageView = MainActivity.getInstance().findViewById(R.id.cover)
    textView = MainActivity.getInstance().findViewById(R.id.textView)
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
    while (true) {
      var handler = HandlerThread(files[count % files.size], captions[count % files.size])
      MainActivity.getInstance().runOnUiThread(handler)
      Thread.sleep(duration * 1000) //Delay
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
    var imageView = MainActivity.getInstance().findViewById<ImageView>(R.id.cover)
    var textView = MainActivity.getInstance().findViewById<TextView>(R.id.textView)
    textView.text = caption

    var id = MainActivity.getInstance().resources.getIdentifier(
      fn,
      "drawable",
      MainActivity.getInstance().packageName
    )
    imageView.setImageResource(id)
  }
}
