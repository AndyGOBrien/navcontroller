package com.llamalabb.navcontroller.products

import com.llamalabb.navcontroller.data.CompaniesDataSource
import com.llamalabb.navcontroller.data.CompaniesRepository
import com.llamalabb.navcontroller.data.Product

/**
 * Created by andy on 10/24/17.
 */
class ProductsPresenter(
        private val companyId: String,
        private val productsView: ProductsContract.View,
        private val companiesRepository: CompaniesRepository)
    : ProductsContract.Presenter{


    private var firstLoad = true

    private val companyName = companiesRepository.cachedCompanies[companyId]!!.name

    init{
        productsView.presenter = this
    }

    override fun onStart() {
        loadProducts(false)
    }

    override fun loadProducts(forceUpdate: Boolean) {
        loadProducts(forceUpdate || firstLoad, false)
        firstLoad = false
    }

    private fun loadProducts(forceUpdate: Boolean, showLoadingUI: Boolean){
        if(showLoadingUI) {
            productsView.setLoadingIndicator(true)
        }

        companiesRepository.getProducts(companyId, object: CompaniesDataSource.LoadProductsCallback{
            override fun onProductsLoaded(products: List<Product>) {
                processProducts(products)
            }

            override fun onDataNotAvailable() {
                productsView.showNoProducts(companyName)
                productsView.setLoadingIndicator(false)
            }

        })

    }

    private fun processProducts(products: List<Product>?){
        products?.let{
            if(it.isEmpty()) {
                productsView.showNoProducts(companyName)
                productsView.setLoadingIndicator(false)
            } else {
                productsView.showProducts(companyName, it)
                productsView.setLoadingIndicator(false)
            }
        } ?: productsView.showNoProducts(companyName)
    }

    override fun openProductPage(product: Product) {
        productsView.showProductPageUi(product.productURL)
    }

    override fun deleteProduct(product: Product) {
        companiesRepository.deleteProduct(product.id)
        loadProducts(true, false)
    }
}