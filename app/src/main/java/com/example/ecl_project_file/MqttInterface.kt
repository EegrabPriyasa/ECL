package com.example.myapplication

import android.app.Activity
import org.eclipse.paho.client.mqttv3.MqttCallback

interface MqttInterface {

    fun publish(topic:String , message: String)
    fun publish(topic: String, message: String, delayMillis: Long)
    fun subscribe(topic: String)
    fun connect()
    fun setCallBack(callback: MqttCallback)
    //fun publish(topic: String, message: String, delayMillis: Long)
}