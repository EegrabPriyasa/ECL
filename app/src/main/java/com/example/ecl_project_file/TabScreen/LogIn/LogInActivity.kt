package com.example.ecl_project_file.TabScreen.LogIn

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ecl_project_file.HelperClasses.SpinnerHelper
import com.example.ecl_project_file.R
import com.example.ecl_project_file.TabScreen.MiniHeel.MiniHeelPageActivity
import com.example.ecl_project_file.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {
  private lateinit var binding: ActivityLogInBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding=ActivityLogInBinding.inflate(layoutInflater)
    setContentView(binding.root)

   //deptSpinner()
    binding.btnLogin.setOnClickListener {
      val intent =Intent(this,MiniHeelPageActivity::class.java)
      startActivity(intent)
      finish()
    }
  }

//  private fun deptSpinner() {
//    SpinnerHelper.setupSpinner(
//      context = this,
//      spinner = binding.deptSpinner,
//      items = listOf("MiniHeel", "MP Converter", "SDP Converter","MP Casting","SDP F","SDP Casting")
//    ) { selected ->
//      // Handle selected item
//      println("Activity Selected: $selected")
//    }
//  }
}