package com.example.thebeatles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
  private var albums = MainActivity.getInstance().albums

  //This tells the system how many cells are desired
  override fun getItemCount(): Int {
    return albums.size
  }

  //This creates a ViewHolder object based on card_layout for each cell
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
    return ViewHolder(v)
  }

  //This initializes the viewHolder to whatever the data source specifies
  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.itemAlbum.text = albums[position].getAlbum()
    holder.itemDate.text = albums[position].getDate()
    holder.itemCover.setImageResource(
      MainActivity.getInstance().resources.getIdentifier(
        albums[position].getFile(), "drawable",
        MainActivity.getInstance().packageName
      )
    )
  }


  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var itemCover: ImageView
    var itemAlbum: TextView
    var itemDate: TextView

    init {
      itemCover = itemView.findViewById(R.id.imageView)
      itemAlbum = itemView.findViewById(R.id.album)
      itemDate = itemView.findViewById(R.id.date)

      var handler = Handler()
      itemView.setOnClickListener(handler)
    }

    inner class Handler() : View.OnClickListener {
      override fun onClick(v: View?) {
        val itemPosition = getLayoutPosition()
        //Get the navigation controller
        var navController = Navigation.findNavController(AlbumFragment.getInstance().requireView())
        val bundle = Bundle()
        bundle.putInt("position", itemPosition)
        navController.navigate(R.id.albumToSong, bundle)
      }
    }
  }
}

