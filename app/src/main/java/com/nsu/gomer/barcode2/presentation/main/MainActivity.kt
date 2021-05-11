package com.nsu.gomer.barcode2.presentation.main

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.nsu.gomer.barcode2.R
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter


class MainActivity : MvpAppCompatActivity(), MainView {

    companion object {
        const val CHOOSE_PICK = 1
    }

    private lateinit var openButton: Button
    private lateinit var scanButton: Button
    private lateinit var image: ImageView

    @InjectPresenter
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        initViews()
    }

    override fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, CHOOSE_PICK)
    }

    override fun initViews() {
        image = findViewById(R.id.imageView)
        openButton = findViewById(R.id.open_button)
        scanButton = findViewById(R.id.scan_button)

        presenter.setScan(false)

        openButton.setOnClickListener {
            presenter.openGallery()
        }
        scanButton.setOnClickListener {
            presenter.scanImage()
        }

    }

    override fun showMessage(message: String?) {
        message?.let {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        } ?: Toast.makeText(this, "String is null", Toast.LENGTH_SHORT).show()
    }

    override fun setScan(isScan: Boolean) {
        scanButton.isEnabled = isScan
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.makeBitmap(image, applicationContext, requestCode, resultCode, data)
    }
}