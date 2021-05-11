package com.nsu.gomer.barcode2.data

import com.nsu.gomer.barcode2.domain.BarcodeResponse
import io.reactivex.Single

interface BarcodeDataSource {
    fun getBarcodeInfo(code: Int): Single<BarcodeResponse>
}