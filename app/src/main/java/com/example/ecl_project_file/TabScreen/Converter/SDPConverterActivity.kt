package com.example.ecl_project_file.TabScreen.Converter

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ecl_project_file.R
import com.example.ecl_project_file.databinding.ActivitySdpconverterBinding

class SDPConverterActivity : AppCompatActivity() {
  private lateinit var binding: ActivitySdpconverterBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding=ActivitySdpconverterBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.includeUserInfo.tvPageTitle.text = "SDP Converter"

  }
}