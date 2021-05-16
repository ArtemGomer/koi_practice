package com.nsu.gomer.barcode2.presentation.info

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.core.view.isVisible
import com.nsu.gomer.barcode2.R
import com.nsu.gomer.barcode2.domain.BarcodeResponse
import com.nsu.gomer.barcode2.presentation.main.MainActivity
import com.squareup.picasso.Picasso
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter

class InfoActivity : MvpAppCompatActivity(), InfoView {

    companion object {

        private const val TAG = "EXTRA_TAG"

        fun start(context: Context, code: Long) {
            val intent = Intent(context, InfoActivity::class.java)
            intent.putExtra(TAG, code)
            context.startActivity(intent)
        }
    }
    @InjectPresenter
    lateinit var infoPresenter: InfoPresenter

    private lateinit var backButton: Button
    private lateinit var nameText: TextView
    private lateinit var companyText: TextView
    private lateinit var productPicture: ImageView
    private lateinit var content: LinearLayout
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        initViews()
        infoPresenter.loadInfo(intent.getLongExtra(TAG, 0))
    }

    private fun initViews() {
        backButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            infoPresenter.openMainActivity()
        }

        nameText = findViewById(R.id.name_text)
        companyText = findViewById(R.id.company_text)
        productPicture = findViewById(R.id.product_image)
        content = findViewById(R.id.info_content)
        progressBar = findViewById(R.id.progress_bar)
    }

    override fun bindInfo(barcodeResponse: BarcodeResponse) {
        nameText.text = barcodeResponse.description
        companyText.text = barcodeResponse.company
        Picasso.with(this).load(barcodeResponse.image_url).into(productPicture)
    }

    override fun openMain() {
        MainActivity.start(this)
    }

    override fun setIsLoading(isLoading: Boolean) {
        progressBar.isVisible = isLoading
        content.isVisible = !isLoading
    }
}