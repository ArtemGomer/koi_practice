package com.nsu.gomer.barcode2.presentation.main

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.squareup.picasso.Picasso
import moxy.MvpAppCompatActivity
import moxy.MvpPresenter

class MainPresenter : MvpPresenter<MainView>() {

    private var barcodeScanner: BarcodeScanner = BarcodeScanning.getClient()
    private lateinit var inputImage: InputImage

    fun openGallery() {
        viewState.openGallery()
    }

    fun setScan(isScan: Boolean) {
        viewState.setScan(isScan)
    }

    fun makeBitmap(
        imageView: ImageView,
        context: Context,
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (requestCode == MainActivity.CHOOSE_PICK && resultCode == MvpAppCompatActivity.RESULT_OK) {
            data?.let { intent ->
                val imageUri = intent.data
                imageUri?.let {
                    Picasso.with(context).load(it).into(imageView)
                    inputImage = InputImage.fromFilePath(context, it)
                    viewState.setScan(true)
                } ?: viewState.showMessage("Can not load picture!")
            } ?: viewState.showMessage("Can not load picture!")
        }
    }

    fun scanImage() {
        barcodeScanner.process(inputImage)
            .addOnSuccessListener {
                if (it.size == 0) {
                    viewState.showMessage("No bar codes in picture!")
                } else {
                    for (barcode in it) {
                        val rawValue = barcode.rawValue
                        viewState.showMessage(rawValue)
                    }
                }
            }
            .addOnFailureListener {
                viewState.showMessage("Can not process picture")
            }
    }

}