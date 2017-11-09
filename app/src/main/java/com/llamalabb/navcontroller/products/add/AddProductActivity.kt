package com.llamalabb.navcontroller.products.add

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.llamalabb.navcontroller.R
import com.llamalabb.navcontroller.data.CompaniesDataSource
import com.llamalabb.navcontroller.data.CompaniesRepository
import com.llamalabb.navcontroller.data.source.CompaniesLocalDataSource
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
        companiesRepository.addCompanyProduct(productName)
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