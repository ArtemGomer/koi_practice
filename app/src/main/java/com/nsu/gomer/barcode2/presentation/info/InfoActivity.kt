package com.nsu.gomer.barcode2.presentation.info

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.nsu.gomer.barcode2.R
import moxy.MvpAppCompatActivity

class InfoActivity : MvpAppCompatActivity(), InfoView {

    companion object {

        private const val TAG = "EXTRA_TAG"

        fun start(context: Context, code: Int) {
            val intent = Intent(context, InfoActivity::class.java)
            intent.putExtra(TAG, code)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
    }
}