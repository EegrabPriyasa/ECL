package com.example.ecl_project_file.Adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ecl_project_file.TabScreen.MiniHeel.MBFProcessFragment

import com.example.ecl_project_file.TabScreen.MiniHeel.MiniHeelPageActivity
import com.example.ecl_project_file.TabScreen.MiniHeel.SIMPProcessFragment
import com.example.ecl_project_file.TabScreen.MiniHeel.MHFProcessFragment

class MiniHeelPageAdapter(miniHeelPageActivity: MiniHeelPageActivity):FragmentStateAdapter(miniHeelPageActivity) {
  override fun getItemCount() = 3

  override fun createFragment(position: Int): Fragment {
    return when (position) {
      0 -> MBFProcessFragment()
      1 -> SIMPProcessFragment()
      else -> MHFProcessFragment()
    }
  }
}