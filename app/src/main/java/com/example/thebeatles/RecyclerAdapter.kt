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

  private var songs = arrayOf(
    Songs("Please Please Me", "March 22, 1963", "pleasepleaseme"),
    Songs("With The Beatles", "November 22, 1963", "with_the_beatles"),
    Songs("A Hard Day's Night", "July 10. 1964", "harddaysnight")
  )

  //This tells the system how many cells are desired
  override fun getItemCount(): Int {
    return songs.size
  }


  //This creates a ViewHolder object based on card_layout for each cell
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
    return ViewHolder(v)
  }

  //This initializes the viewHolder to whatever the data source specifies
  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.itemTitle.text = songs[position].getName()
    holder.itemDetail.text = songs[position].getDate()
    holder.itemImage.setImageResource(
      MainActivity.getInstance().resources.getIdentifier(
        songs[position].getPic(), "drawable",
        MainActivity.getInstance().packageName
      )
    )
  }


  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var itemImage: ImageView
    var itemTitle: TextView
    var itemDetail: TextView

    init {
      itemImage = itemView.findViewById(R.id.imageView)
      itemTitle = itemView.findViewById(R.id.title)
      itemDetail = itemView.findViewById(R.id.detail)

      var handler = Handler()
      itemView.setOnClickListener(handler)
    }

    inner class Handler() : View.OnClickListener {
      override fun onClick(v: View?) {
        val itemPosition = getLayoutPosition()
        //Get the navigation controller
        var navController = Navigation.findNavController(MainFragment.getInstance().requireView()!!)
        val bundle = Bundle()
        bundle.putInt("position", itemPosition)
        navController.navigate(R.id.albumToSong, bundle)
      }
    }


  }
}

