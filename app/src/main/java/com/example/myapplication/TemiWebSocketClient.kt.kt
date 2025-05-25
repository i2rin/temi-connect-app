package com.example.myapplication

import android.util.Log
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

class TemiWebSocketClient(serverUri: String) : WebSocketClient(URI(serverUri)) {

    override fun onOpen(handshakedata: ServerHandshake?) {
        Log.d("WebSocket", "Connection opened")
    }

    override fun onMessage(message: String?) {
        Log.d("WebSocket", "Message received: $message")
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        Log.d("WebSocket", "Connection closed: $reason")
    }

    override fun onError(ex: Exception?) {
        Log.e("WebSocket", "Error occurred", ex)
    }

    fun disconnect() {
        if (this.isOpen) {
            this.close(1000, "Client disconnecting")
            Log.d("WebSocket", "Disconnecting WebSocket client")
        }
    }
}
