package com.example.thebeatles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.InputStreamReader

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
    holder.itemProducer.text = songs[pos].getProducer()
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

    init {
      itemSong = itemView.findViewById(R.id.song)
      itemProducer = itemView.findViewById(R.id.producer)
    }
  }
}
