package com.nsu.gomer.barcode2.data

import com.nsu.gomer.barcode2.domain.BarcodeResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path


interface BarcodeApi {

    @GET("{code}")
    fun getBarcodeInfo(@Path("code") code: Int): Single<BarcodeResponse>

}