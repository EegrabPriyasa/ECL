package com.example.ecl_project_file.TabScreen.MiniHeel

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.ecl_project_file.Adapters.MiniHeelPageAdapter
import com.example.ecl_project_file.ConnectionServices.ConnectionStates
import com.example.ecl_project_file.HelperClasses.SpinnerHelper
import com.example.ecl_project_file.MqttSetup.MQTTClientBuilder
import com.example.ecl_project_file.ViewModel.ConnectionViewModel
import com.example.ecl_project_file.databinding.ActivityMiniHeelPageBinding
import com.google.android.material.tabs.TabLayoutMediator

class MiniHeelPageActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMiniHeelPageBinding
  private val tabTitles = arrayOf("MBF Process", "SIMP Process", "MHF Process")
  private val mqttViewModel: ConnectionViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMiniHeelPageBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // Attach adapter
    binding.viewPager.adapter = MiniHeelPageAdapter(this)
    TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
      tab.text = tabTitles[position]
    }.attach()

    mhfSpinner()

    mqttViewModel.connectionStatus.observe(this, Observer { status ->
      when (status) {
        ConnectionStates.CONNECTED -> showMessage("Connecting to MQTT")
        ConnectionStates.DISCONNECTED -> showMessage("MQTT Disconnected ❌")
        ConnectionStates.CONNECTING -> showMessage("MQTT connected ")
        ConnectionStates.BACKEND_ONLINE -> showMessage("Backend is ONLINE 🟢")
        ConnectionStates.BACKEND_OFFLINE -> showMessage("Backend is OFFLine 🟢")
      }
    })

    mqttViewModel.connect(this)
    mqttViewModel.checkBackend()


  }
  private fun showMessage(msg: String) {
    android.widget.Toast.makeText(this, msg, android.widget.Toast.LENGTH_SHORT).show()
  }
  private fun mhfSpinner() {
    SpinnerHelper.setupSpinner(
      context = this,
      spinner = binding.includeUserInfo.MhfSpinner,
      items = listOf("MHF 1","MHF 2","MHF 3","MHF 4","MHF 5")
    ) { selected ->
      // Handle selected item
      println("Activity Selected: $selected")
    }
    SpinnerHelper.setupSpinner(
      context = this,
      spinner = binding.mhfTempReading.spinnerMhf,
      items = listOf("MHF 1","MHF 2","MHF 3","MHF 4","MHF 5")
    )
    SpinnerHelper.setupSpinner(
      context = this,
      spinner = binding.mhfTempReading.spMhfNumber,
      items = listOf("MHF 1","MHF 2","MHF 3","MHF 4","MHF 5")
    )
  }

}


