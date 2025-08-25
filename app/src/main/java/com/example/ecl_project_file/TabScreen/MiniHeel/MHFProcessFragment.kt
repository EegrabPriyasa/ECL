package com.example.ecl_project_file.TabScreen.MiniHeel

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.ecl_project_file.R



class MHFProcessFragment : Fragment() {


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment

    val view = inflater.inflate(R.layout.fragment_third, container, false)

    val button = view.findViewById<Button>(R.id.btnMHFLadleOut)

    return view
  }


}