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

object MQTTClientBuilder  {

    private const val SERVER_URI = "ws://15.206.40.168:9883" // ✅ Replace with your broker URI
    private const val USERNAME = "mqtt"
    private const val PASSWORD = "temppwd"

    private var mqttClient: MqttAndroidClient? = null

    /** ✅ Initialize or return existing client */
    fun getInstance(context: Context): MqttAndroidClient {
        if (mqttClient == null) {
            mqttClient = MqttAndroidClient(
                context,
                SERVER_URI,
                "AndroidClient-${System.currentTimeMillis()}"
            )
        }
        return mqttClient!!
    }

    /** ✅ Connect with callbacks */
    fun connect(
        context: Context,
        onConnected: () -> Unit,
        onDisconnected: (Throwable?) -> Unit,
        onConnecting: () -> Unit
    ) {
        val client = getInstance(context)
        val options = MqttConnectOptions().apply {
            isAutomaticReconnect = true
            isCleanSession = true
            userName = USERNAME
            password = PASSWORD.toCharArray()
            connectionTimeout = 10
        }

        onConnecting()

        try {
            client.connect(options, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d("MQTT", "✅ Connected to broker")
                    onConnected()
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.e("MQTT", "❌ Connection failed: ${exception?.message}")
                    onDisconnected(exception)
                }
            })
        } catch (e: MqttException) {
            Log.e("MQTT", "❌ Exception while connecting: ${e.message}")
            onDisconnected(e)
        }
    }

    /** ✅ Disconnect */
    fun disconnect(onDisconnected: () -> Unit) {
        mqttClient?.disconnect(null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.d("MQTT", "✅ Disconnected")
                onDisconnected()
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.e("MQTT", "❌ Disconnect failed: ${exception?.message}")
                onDisconnected()
            }
        })
    }

    /** ✅ Publish */
    fun publish(topic: String, msg: String, qos: Int = 1, retained: Boolean = false) {
        try {
            val message = MqttMessage(msg.toByteArray()).apply {
                this.qos = qos
                isRetained = retained
            }
            mqttClient?.publish(topic, message)
            Log.d("MQTT", "📤 Published to $topic: $msg")
        } catch (e: Exception) {
            Log.e("MQTT", "❌ Publish failed: ${e.message}")
        }
    }

    /** ✅ Subscribe */
    fun subscribe(topic: String, qos: Int = 1, onMessage: (String) -> Unit) {
        try {
            mqttClient?.subscribe(topic, qos, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d("MQTT", "📥 Subscribed to $topic")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.e("MQTT", "❌ Failed to subscribe $topic: ${exception?.message}")
                }
            })

            mqttClient?.setCallback(object : MqttCallback {
                override fun messageArrived(topic: String?, message: MqttMessage?) {
                    message?.let { onMessage(it.toString()) }
                }

                override fun connectionLost(cause: Throwable?) {
                    Log.e("MQTT", "⚠️ Connection lost: ${cause?.message}")
                }

                override fun deliveryComplete(token: IMqttDeliveryToken?) {}
            })
        } catch (e: MqttException) {
            Log.e("MQTT", "❌ Subscribe failed: ${e.message}")
        }
    }

    /** ✅ Backend "ping" check */
    fun checkBackendConnection(onResult: (Boolean) -> Unit) {
        Thread {
            try {
                val url = java.net.URL("https://your-backend.com/health") // 🔹 Replace with real API
                val conn = url.openConnection() as java.net.HttpURLConnection
                conn.connectTimeout = 5000
                conn.requestMethod = "GET"
                val code = conn.responseCode
                onResult(code == 200)
            } catch (e: Exception) {
                onResult(false)
            }
        }.start()
    }


}