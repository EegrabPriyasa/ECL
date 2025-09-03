package com.example.ecl_project_file.TabScreen.MiniHeel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ecl_project_file.HelperClasses.SpinnerHelper
import com.example.ecl_project_file.R
import com.example.ecl_project_file.databinding.FragmentFirst2Binding


class MBFProcessFragment : Fragment() {

  private var _binding: FragmentFirst2Binding? = null
  private val binding get() = _binding!!

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

  }
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = FragmentFirst2Binding.inflate(inflater, container, false)
    return binding.root
  }
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    mbfSpinner() // Safe to use binding here
  }
  private fun mbfSpinner() {
    SpinnerHelper.setupSpinner(
      context = requireActivity(),
      spinner = _binding!!.spinnerLadleSeqMBF ,
      items = listOf("Ladle 1","Ladle 2","Ladle 3")
    )
  }
  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }


}