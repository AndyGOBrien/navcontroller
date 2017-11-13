package com.llamalabb.navcontroller.products.add

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.llamalabb.navcontroller.R
import com.llamalabb.navcontroller.R.id.add_button
import com.llamalabb.navcontroller.data.CompaniesRepository
import com.llamalabb.navcontroller.data.Product
import com.llamalabb.navcontroller.data.source.CompaniesLocalDataSource
import com.llamalabb.navcontroller.products.ProductsActivity
import kotlinx.android.synthetic.main.add_product_act.*

/**
 * Created by andy on 11/2/17.
 */
class AddProductActivity : AppCompatActivity(){

    private lateinit var companiesRepository: CompaniesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_product_act)

        companiesRepository = CompaniesRepository.getInstance(
                CompaniesLocalDataSource.getInstance(this))

        setClickListeners()

    }

    private fun addButtonAction() {
        val productName = product_name_editText.text.toString()
        val companyId = intent.getStringExtra(ProductsActivity.COMPANY_ID)
        val productPageUrl = product_url_editText.text.toString()
        val productLogoUrl = product_logo_editText.text.toString()
        val product = Product(
                productName,
                companyId,
                productLogoUrl.let{if(it.isBlank()) null else it},
                productPageUrl.let{if(it.isBlank()) null else it}
        )
        companiesRepository.saveProduct(product)
        finish()
    }

    private fun setClickListeners(){
        val clickListener = View.OnClickListener {
            when (it) {
                add_button -> addButtonAction()
            }
        }
        add_button.setOnClickListener(clickListener)
    }


}