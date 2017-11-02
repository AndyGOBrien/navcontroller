package com.llamalabb.navcontroller.products

import com.llamalabb.navcontroller.BasePresenter
import com.llamalabb.navcontroller.BaseView
import com.llamalabb.navcontroller.data.Product

/**
 * Created by andy on 10/24/17.
 */
interface ProductsContract {
    interface View : BaseView<Presenter>{
        fun showProducts(list: List<Product>)
        fun showNoProducts()
        fun setLoadingIndicator(active: Boolean)
    }

    interface Presenter : BasePresenter {
        fun loadProducts(forceUpdate: Boolean)
    }

}