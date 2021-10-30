package com.example.thebeatles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class AlbumFragment : Fragment() {
  private var layoutManager: RecyclerView.LayoutManager? = null
  private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

  companion object {
    private var instance: AlbumFragment? = null
    public fun getInstance(): AlbumFragment {
      return instance!!
    }
  }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    instance = this
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_album, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    layoutManager = LinearLayoutManager(MainActivity.getInstance())
    var recycler_view = MainActivity.getInstance().findViewById<RecyclerView>(R.id.recycler_view)
    recycler_view.layoutManager = layoutManager
    adapter = RecyclerAdapter()
    recycler_view.adapter = adapter

  }
}
