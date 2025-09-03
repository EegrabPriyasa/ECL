package com.example.ecl_project_file.TabScreen.MiniHeel

import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.ecl_project_file.Adapters.MiniHeelPageAdapter
import com.example.ecl_project_file.HelperClasses.SpinnerHelper
import com.example.ecl_project_file.MqttSetup.MQTTClientBuilder


import com.example.ecl_project_file.databinding.ActivityMiniHeelPageBinding
import com.example.ecl_project_file.databinding.LayoutUserHeaderBinding
import com.example.ecl_project_file.databinding.MhfTempReadingBinding
import com.example.ecl_project_file.databinding.ProductionProcessBinding
import com.google.android.material.tabs.TabLayoutMediator
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MiniHeelPageActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMiniHeelPageBinding
  private val tabTitles = arrayOf("MBF Process", "SIMP Process", "MHF Process")
//  private val mqttViewModel: ConnectionViewModel by viewModels()
  private var mqttClientBuilder: MQTTClientBuilder?=null
  private lateinit var layoutUserHeaderBinding :LayoutUserHeaderBinding
  private lateinit var mhfTempReadingBinding: MhfTempReadingBinding
  private lateinit var productionProcessBinding: ProductionProcessBinding
  private var selectedMhf: String? = null


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMiniHeelPageBinding.inflate(layoutInflater)
    mhfTempReadingBinding = MhfTempReadingBinding.bind(binding.mhfTempReading.root)
    productionProcessBinding =ProductionProcessBinding.bind(binding.productionProcess.root)
    layoutUserHeaderBinding=LayoutUserHeaderBinding.bind(binding.includeUserInfo.root)

    setContentView(binding.root)

    logInProcess()

    //mqtt Connection//
    mqttClientBuilder = MQTTClientBuilder.getInstance(applicationContext)
    mqttClientBuilder!!.connect()

    // Attach adapter//
    binding.viewPager.adapter = MiniHeelPageAdapter(this)
    TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
      tab.text = tabTitles[position]
    }.attach()

    //Spinner Process//
    mhfSpinner()



//    mqttViewModel.connectionStatus.observe(this, Observer { status ->
//      when (status) {
//        ConnectionStates.CONNECTED -> showMessage("Connecting to MQTT")
//        ConnectionStates.DISCONNECTED -> showMessage("MQTT Disconnected ❌")
//        ConnectionStates.CONNECTING -> showMessage("MQTT connected ")
//        ConnectionStates.BACKEND_ONLINE -> showMessage("Backend is ONLINE 🟢")
//        ConnectionStates.BACKEND_OFFLINE -> showMessage("Backend is OFFLine 🟢")
//      }
//    })

//    mqttViewModel.connect(this)
//    mqttViewModel.checkBackend()

    liveTemperature()



  }

  private fun logInProcess() {

    val greeting = "Shift: " + getCurrentShift()
    layoutUserHeaderBinding.tvShift.setText(greeting)
    layoutUserHeaderBinding.sessionDate.text =getFormattedDate()
    layoutUserHeaderBinding.sessionTime.text =getFormattedTime()
  }

  private fun liveTemperature() {
    mqttClientBuilder?.let {

      mqttClientBuilder?.setCallBack(object : MqttCallback {

        override fun connectionLost(cause: Throwable?) {

        }

        override fun messageArrived(topic: String?, message: MqttMessage?) {

          val topictxt = topic.toString()
          Log.d("TAG", "topicText :$topictxt")
          Log.d("TAG", "Sign in message a${message.toString()}a from topic: $topic")
          var value = message.toString()
          Log.d("TAG", "b value is " + value)
          if (topictxt == "MHF/FURN/TEMP1") //for Live Temperature//
          {
            mhfTempReadingBinding.tvSelectedTemp.text =value
          }

        }
        override fun deliveryComplete(token: IMqttDeliveryToken?) {
        }
      })

    }
  }

  fun getCurrentShift(): String
  { val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    val currentTime = sdf.format(Date())
    return when
    { currentTime >= "06:00" && currentTime < "14:00" -> "A"
      currentTime >= "14:00" && currentTime < "22:00" -> "B"
      else -> "C" } }


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
    ){ selected ->
      selectedMhf = selected   // save selected value
      println("Spinner Selected: $selected")
    }

    SpinnerHelper.setupSpinner(
      context = this,
      spinner = binding.mhfTempReading.spMhfNumber,
      items = listOf("MHF 1","MHF 2","MHF 3","MHF 4","MHF 5")
    )
    SpinnerHelper.setupSpinner(
      context = this,
      spinner = binding.mhfTempReading.spinner50T,
      items = listOf("MBF To SIMP","MBF To MHF")
    )
    SpinnerHelper.setupSpinner(
      context = this,
      spinner = binding.mhfTempReading.spinner15T,
      items = listOf("SIMP To MHF","MHF To SIMP","MHF To Fitting","MHF To SDP","Fe-Si pour in MHF","MS pour in MHF")
    )
    SpinnerHelper.setupSpinner(
      context = this,
      spinner = binding.mhfTempReading.spinner10T,
      items = listOf("MHF To MP Converter")
    )
  }

  fun getFormattedDate(): String {
    val calendar = Calendar.getInstance()
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    // Add suffix (st, nd, rd, th)
    val suffix = when {
      day in 11..13 -> "th"
      day % 10 == 1 -> "st"
      day % 10 == 2 -> "nd"
      day % 10 == 3 -> "rd"
      else -> "th"
    }

    val dayName = SimpleDateFormat("EEE", Locale.getDefault()).format(calendar.time) // Fri
    val monthName = SimpleDateFormat("MMM", Locale.getDefault()).format(calendar.time) // Aug

    return "$dayName, ${day}${suffix} $monthName"
  }

  fun getFormattedTime(): String {
    return SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()) // 11:15:00
  }



}


