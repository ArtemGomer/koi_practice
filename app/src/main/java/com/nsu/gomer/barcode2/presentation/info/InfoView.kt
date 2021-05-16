package com.nsu.gomer.barcode2.presentation.info

import com.nsu.gomer.barcode2.domain.BarcodeResponse
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface InfoView: MvpView {
    fun openMain()
    fun bindInfo(barcodeResponse: BarcodeResponse)
    fun setIsLoading(isLoading: Boolean)
}