package com.nsu.gomer.barcode2.presentation.info

import com.nsu.gomer.barcode2.RetrofitHolder
import com.nsu.gomer.barcode2.data.BarcodeDataSource
import com.nsu.gomer.barcode2.data.BarcodeRemoteDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class InfoPresenter: MvpPresenter<InfoView>() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private fun Disposable.untilDestroy() {
        compositeDisposable.add(this)
    }

    private val barcodeDataSource: BarcodeDataSource by lazy {
        BarcodeRemoteDataSource(RetrofitHolder.barcodeApi)
    }

    fun openMainActivity() {
        viewState.openMain()
    }

    fun loadInfo(code: Long) {
        viewState.setIsLoading(true)
        barcodeDataSource.getBarcodeInfo(code)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.setIsLoading(false)
                viewState.bindInfo(it)
            },{
                it?.printStackTrace()
                viewState.openMain()
            }).untilDestroy()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

}