package com.nsu.gomer.barcode2.domain

data class BarcodeResponse(
    val `class`: String,
    val code: String,
    val company: String,
    val description: String,
    val image_url: String
)