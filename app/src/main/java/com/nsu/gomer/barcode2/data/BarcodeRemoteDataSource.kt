package com.nsu.gomer.barcode2.data

import com.nsu.gomer.barcode2.domain.BarcodeResponse
import io.reactivex.Single

class BarcodeRemoteDataSource(private val barcodeApi: BarcodeApi): BarcodeDataSource {
    override fun getBarcodeInfo(code: Long): Single<BarcodeResponse> =
        barcodeApi.getBarcodeInfo(code)
}