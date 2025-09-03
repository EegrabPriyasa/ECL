package com.example.ecl_project_file.MqttSetup

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log

import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage

object MQTTClientBuilder :MqttInterface {

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
    var clientId:String?="RPi3${System.currentTimeMillis()}"

    fun getInstance(context: Context): MQTTClientBuilder {
        if (mqttClient == null || mqttClient?.isConnected == false) {
            try {
                mqttClient?.unregisterResources() // helps with stale state
                mqttClient?.close()
                mqttClient = null
            } catch (e: Exception) {
                e.printStackTrace()
            }

            //mqttClient = null
            clientId = "RPi3${System.currentTimeMillis()}"
            mqttClient = MqttAndroidClient(context.applicationContext, serverURI, clientId)
        }
        return this
    }

    override fun publish(topic: String, msg: String) {
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

    override fun publish(topic: String, message: String, delayMillis: Long) {
        TODO("Not yet implemented")
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
                mqttClient?.connect(setUpConnectionOptions(USERNAME, PASSWORD)
                    , null, object : IMqttActionListener {
                        override fun onSuccess(asyncActionToken: IMqttToken?) {
                            subscribe("akm/d2s/uuid")
                            subscribe("MHF/FURN/TEMP1") //d2s./kid
                            subscribe("akm/d2s/kid")

                        }

                        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                            Log.d(TAG, "Connection failure: ${exception!!.printStackTrace()}" )
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
        connOpts.isCleanSession = false
        connOpts.userName = username
        connOpts.password = password.toCharArray()

        return connOpts
    }


}