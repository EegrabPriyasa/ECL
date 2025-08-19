package com.example.ecl_project_file

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ecl_project_file.MqttSetup.MQTTClientBuilder

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_main)
    val mqttClient = MQTTClientBuilder.getInstance(this)
    mqttClient.connect()

  }
}