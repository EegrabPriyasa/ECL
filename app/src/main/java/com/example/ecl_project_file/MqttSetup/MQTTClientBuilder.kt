package com.example.ecl_project_file.MqttSetup

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log

import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage

object MQTTClientBuilder : MqttInterface {

    private val TAG = MQTTClientBuilder::class.java.name

    val serverURI = "ws://15.206.40.168:9883"
    var USERNAME = "mqtt"
    var PASSWORD = "temppwd"
//
//    var serverURI = "tcp://10.232.233.179:8883"//"ws://15.206.40.168:9883"//"tcp://10.232.233.179:8883"//"wss://bl7gvpdm1001.dir.svc.accenture.com:8886"//"tcp://52.172.178.92:1883"//"tcp://abhinesh@broker.emqx.io:1883"
//    var USERNAME = "mqtt_wishkey"//"mqtt_wishkey"//"mqtt"
//    var PASSWORD = "Eegrab@123"//"Eegrab@123"//"temppwd"

     var mqttClient:MqttAndroidClient?=null
     //var serverURI = "ws://15.206.40.168:9883"
     var clientId:String?=null

    fun getInstance(context: Context) : MQTTClientBuilder {
        if (mqttClient == null) {
            clientId = "RPi3${System.currentTimeMillis()}"
            mqttClient = MqttAndroidClient(context, serverURI, clientId)

            return this
        }
        return this
    }


    override fun publish(topic: String , msg: String) {

        try {
            val message = MqttMessage()
            message.payload = msg.toByteArray()
            message.qos = 0
            message.isRetained = false
            mqttClient?.publish(topic, message, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
//                                                Log.d(TAG, "$msg published to $topic")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    //                        Log.d(TAG, "Failed to publish $msg to $topic")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }

    }

    override fun publish(topic: String, msg: String, delayMillis: Long) {

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val mqttMessage = MqttMessage(msg.toByteArray())
            mqttClient?.publish(topic, mqttMessage)
            println("Message published after $delayMillis milliseconds")
        }, delayMillis)
    }


    override fun subscribe(topic: String) {

        mqttClient?.let {

            try {
                mqttClient?.subscribe(topic, 1, null, object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                        Log.d("status", "Subscribed to $topic")
                    }

                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                        Log.d(TAG, "Failed to subscribe $topic")
                    }
                })
            } catch (e: MqttException) {
                e.printStackTrace()
            }
        }


    }

    override fun connect() {

        mqttClient?.let {

            try {
                mqttClient!!.connect(
                  setUpConnectionOptions(USERNAME, PASSWORD)
                    , null, object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                        subscribe("akm/d2s/uuid")
                        subscribe("akm/d2s/emlock") //d2s./kid
                        subscribe("akm/d2s/kid")
                        Log.d("TAG", "Connection Success")

                    }

                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                        Log.e("TAG", "Connection failure: ${exception?.message}", exception)
                    }

                })
            } catch (e: MqttException) {
                e.printStackTrace()
            }

        }

    }

    override fun setCallBack(callback: MqttCallback) {

        mqttClient?.let {

            mqttClient!!.setCallback(callback)
        }

    }

    fun setUpConnectionOptions(username: String, password: String): MqttConnectOptions? {
      val connOpts = MqttConnectOptions()
      connOpts.isCleanSession = true
      connOpts.userName = username
      connOpts.password = password.toCharArray()
      return connOpts
    }


}