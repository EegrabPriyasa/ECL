package com.example.ecl_project_file.TabScreen.MiniHeel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ecl_project_file.AdapterClass.MiniHeelPageAdapter
import com.example.ecl_project_file.databinding.ActivityMiniHeelPageBinding
import com.google.android.material.tabs.TabLayoutMediator

class MiniHeelPageActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMiniHeelPageBinding
  private val tabTitles = arrayOf("MBF Process", "SIMP Process", "MHF Process")

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMiniHeelPageBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val fragments = listOf(
      MBFProcessFragment(),
      SIMPProcessFragment(),
      MHFProcessFragment()
    )

    // Attach adapter
    binding.viewPager.adapter = MiniHeelPageAdapter(this)
    TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
      tab.text = tabTitles[position]
    }.attach()


  }
}