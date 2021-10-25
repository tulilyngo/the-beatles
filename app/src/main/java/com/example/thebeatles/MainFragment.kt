package com.example.thebeatles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {
  private var pic: ImageView? = null
  private var title: TextView? = null

  companion object {
    private var instance: MainFragment? = null
    public fun getInstance(): MainFragment {
      return instance!!
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_main, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    instance = this

    var slideShow = SlideShow(3, 16)
    slideShow.start()
  }
}
