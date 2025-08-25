package com.example.ecl_project_file.TabScreen.Casting

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ecl_project_file.R
import com.example.ecl_project_file.databinding.ActivityMpcastingBinding

class MPCastingActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMpcastingBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding=ActivityMpcastingBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.includeUserInfo.tvPageTitle.text= "MP Casting"
    binding.ladleToHopperSelection.alertLayout.tvAlertMessage.text ="Accept ladle temperature before selecting CCM machines"


  }
}