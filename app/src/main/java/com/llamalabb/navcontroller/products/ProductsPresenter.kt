package com.llamalabb.navcontroller.products

import com.llamalabb.navcontroller.data.DataManager
import com.llamalabb.navcontroller.data.Product

/**
 * Created by andy on 10/24/17.
 */
class ProductsPresenter(val productsView: ProductsContract.View) :
        ProductsContract.Presenter{

    private var firstLoad = true

    init{
        productsView.presenter = this
    }

    override fun onStart() {
        loadProducts(false)
    }

    override fun loadProducts(forceUpdate: Boolean) {
        loadProducts(forceUpdate || firstLoad, true)
        firstLoad = false
    }

    private fun loadProducts(forceUpdate: Boolean, showLoadingUI: Boolean){
        if(showLoadingUI) {
            productsView.setLoadingIndicator(true)
        }

        val products = DataManager.getCompanyProductList()
        processProducts(products)

        productsView.setLoadingIndicator(false)
    }

    private fun processProducts(products: List<Product>){
        with(DataManager){
            val companyName = getCompanyList()[companyNum].name

            if(products.isEmpty())
                productsView.showNoProducts(companyName)
            else
                productsView.showProducts(companyName, products)
        }
    }
}