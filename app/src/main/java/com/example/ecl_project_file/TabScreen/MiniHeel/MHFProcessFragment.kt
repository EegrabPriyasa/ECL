package com.example.ecl_project_file.TabScreen.MiniHeel

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.ecl_project_file.HelperClasses.SpinnerHelper
import com.example.ecl_project_file.R
import com.example.ecl_project_file.databinding.FragmentFirst2Binding
import com.example.ecl_project_file.databinding.FragmentThirdBinding


class MHFProcessFragment : Fragment() {
  private var _binding: FragmentThirdBinding? = null
  private val binding get() = _binding!!

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment

    _binding = FragmentThirdBinding.inflate(inflater, container, false)
    return binding.root
  }
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    //mbfSpinner() // Safe to use binding here
  }

//  private fun mbfSpinner() {
//    SpinnerHelper.setupSpinner(
//      context = requireActivity(),
//      spinner = _binding!!.thirdFragmentUpperPart.spDeptMHF ,
//      items = listOf("MP","SDP","Fitting")
//    )
//  }
//  override fun onDestroyView() {
//    super.onDestroyView()
//    _binding = null
//  }


}