package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.robotemi.sdk.Robot
import com.robotemi.sdk.TtsRequest


class MainActivity : ComponentActivity() {

    private lateinit var webSocketClient: TemiWebSocketClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 画像認識システムに接続する
        webSocketClient = TemiWebSocketClient("ws://10.0.2.2:8765") // ここをAIシステムのIPに置き換える
        webSocketClient.connect()

        // ロボットを認証
        Robot.getInstance().addOnRobotReadyListener(object : com.robotemi.sdk.listeners.OnRobotReadyListener {
            override fun onRobotReady(isReady: Boolean) {
                if (isReady) {
                    Robot.getInstance().speak(
                        TtsRequest.create("Temi proctor system initialized.", false)
                    )
                }
            }
        })

        // UIを表示
        setContent {
            MyApplicationTheme {
                Greeting("Temi")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        webSocketClient.disconnect()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}