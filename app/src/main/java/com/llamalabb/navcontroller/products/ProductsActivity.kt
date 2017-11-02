package com.llamalabb.navcontroller.products

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.llamalabb.navcontroller.R
import com.llamalabb.navcontroller.util.replaceFragmentInActivity

class ProductsActivity : AppCompatActivity(), ProductsFragment.ProductFragmentListener {
    private lateinit var productsPresenter: ProductsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.products_act)

        val productsFragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
                as ProductsFragment? ?:ProductsFragment.newInstance().also{
            replaceFragmentInActivity(it, R.id.contentFrame)
        }

        productsPresenter = ProductsPresenter(productsFragment).apply {
            if(savedInstanceState != null){
            }
        }
    }

}
