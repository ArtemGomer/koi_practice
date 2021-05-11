package com.nsu.gomer.barcode2.presentation.main

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView: MvpView {

    fun openGallery()
    fun initViews()
    fun setScan(isScan: Boolean)
    fun showMessage(message: String?)
    fun openInfoActivity(code: Long?)
}