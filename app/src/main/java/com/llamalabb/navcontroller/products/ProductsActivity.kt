package com.llamalabb.navcontroller.products

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.llamalabb.navcontroller.R
import com.llamalabb.navcontroller.data.CompaniesRepository
import com.llamalabb.navcontroller.data.source.CompaniesLocalDataSource
import com.llamalabb.navcontroller.products.add.AddProductActivity
import com.llamalabb.navcontroller.util.replaceFragmentInActivity
import kotlinx.android.synthetic.main.products_act.*

class ProductsActivity : AppCompatActivity(), ProductsFragment.ProductFragmentListener {
    private lateinit var productsPresenter: ProductsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.products_act)

        val productsFragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
                as ProductsFragment? ?:ProductsFragment.newInstance().also{
            replaceFragmentInActivity(it, R.id.contentFrame)
        }

        productsPresenter = ProductsPresenter(productsFragment, CompaniesRepository.getInstance(
                CompaniesLocalDataSource.getInstance(this))).apply {

            if(savedInstanceState != null){

            }
        }

        setClickListeners()
    }

    private fun setClickListeners(){
        val clickListener = View.OnClickListener {
            when (it) {
                fab_add_product -> fabClickAction()
            }
        }
        fab_add_product.setOnClickListener(clickListener)
    }

    private fun fabClickAction(){

        startActivity(Intent(this, AddProductActivity::class.java))

    }

}
