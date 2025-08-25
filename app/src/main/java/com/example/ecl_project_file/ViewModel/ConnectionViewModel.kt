package com.example.ecl_project_file.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecl_project_file.ConnectionServices.ConnectionStates
import com.example.ecl_project_file.MqttSetup.MQTTClientBuilder

class ConnectionViewModel :ViewModel()
{
  private val _connectionStatus = MutableLiveData<ConnectionStates>()
  val connectionStatus: LiveData<ConnectionStates> get() = _connectionStatus

  fun connect(context: Context) {
    MQTTClientBuilder.connect(
      context,
      onConnected = { _connectionStatus.postValue(ConnectionStates.CONNECTED) },
      onDisconnected = { _connectionStatus.postValue(ConnectionStates.DISCONNECTED) },
      onConnecting = { _connectionStatus.postValue(ConnectionStates.CONNECTING) }
    )
  }

  fun disconnect() {
    MQTTClientBuilder.disconnect {
      _connectionStatus.postValue(ConnectionStates.DISCONNECTED)
    }
  }

  fun checkBackend() {
    MQTTClientBuilder.checkBackendConnection { isOnline ->
      _connectionStatus.postValue(
        if (isOnline) ConnectionStates.BACKEND_ONLINE
        else ConnectionStates.BACKEND_OFFLINE
      )
    }
  }

}