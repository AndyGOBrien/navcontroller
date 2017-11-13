package com.llamalabb.navcontroller.products

import com.llamalabb.navcontroller.BasePresenter
import com.llamalabb.navcontroller.BaseView
import com.llamalabb.navcontroller.data.Product

/**
 * Created by andy on 10/24/17.
 */
interface ProductsContract {
    interface View : BaseView<Presenter>{
        fun showProducts(companyName: String, list: List<Product>)
        fun showNoProducts(companyName: String)
        fun setLoadingIndicator(active: Boolean)
        fun showProductPageUi(productUrl: String?)
    }

    interface Presenter : BasePresenter {
        fun loadProducts(forceUpdate: Boolean)
        fun openProductPage(product: Product)
        fun deleteProduct(product: Product)
    }

}