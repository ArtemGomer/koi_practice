package com.example.parsing

import Parser
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val thread = Thread {
            run() {
                try {
                    val parser = Parser()
                    parser.parseCategory("/catalog/voda-soki-napitki/", "Вода, соки и напитки")
                } catch (exception: IOException) {
                    exception.printStackTrace()
                }
            }
        }
        thread.start()
    }
}