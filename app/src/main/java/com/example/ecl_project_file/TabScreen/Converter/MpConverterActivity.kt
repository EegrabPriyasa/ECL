package com.example.ecl_project_file.TabScreen.Converter

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ecl_project_file.R
import com.example.ecl_project_file.databinding.ActivityMpConverterBinding

class MpConverterActivity : AppCompatActivity() {
  private lateinit var binding :ActivityMpConverterBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding=ActivityMpConverterBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.includeUserInfo.tvPageTitle.text = "MP Converter"

  }
}