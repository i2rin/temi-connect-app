package com.example.myapplication

import android.util.Log
import com.robotemi.sdk.Robot
import okhttp3.*

class `TemiWebSocketClient.kt`(private val serverUrl: String) {

    private val client = OkHttpClient()
    private lateinit var webSocket: WebSocket

    fun connect() {
        val request = Request.Builder()
            .url(serverUrl)
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d("TemiWebSocket", "WebSocket Connected")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.d("TemiWebSocket", "Message received: $text")
                handleCommand(text)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.e("TemiWebSocket", "WebSocket error: ${t.message}")
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                Log.d("TemiWebSocket", "WebSocket closing: $reason")
                webSocket.close(1000, null)
            }
        })
    }

    private fun handleCommand(command: String) {
        val robot = Robot.getInstance()
        when (command.trim()) {
            "WARNING" -> robot.speak("Please stay focused on your exam.")
            "FOCUS_USER" -> robot.speak("I am watching you more closely.")
            "MOVE_CLOSER" -> robot.turnBy(30) // or robot.goTo("position")
            else -> robot.speak("Unknown command: $command")
        }
    }

    fun disconnect() {
        webSocket.close(1000, "App closing")
    }
}
