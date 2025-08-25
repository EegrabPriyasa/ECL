package com.example.ecl_project_file.TabScreen.Converter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ecl_project_file.databinding.ActivityMpConverterBinding

class MPConverterActivity : AppCompatActivity() {
  private lateinit var binding :ActivityMpConverterBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding=ActivityMpConverterBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.includeUserInfo.tvPageTitle.text = "MP Converter"

  }
}