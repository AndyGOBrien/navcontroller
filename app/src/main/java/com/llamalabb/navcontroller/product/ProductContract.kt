package com.llamalabb.navcontroller.product

import com.llamalabb.navcontroller.BasePresenter
import com.llamalabb.navcontroller.BaseView
import com.llamalabb.navcontroller.data.Product

/**
 * Created by andy on 10/24/17.
 */
interface ProductContract {
    interface View : BaseView<Presenter>{
        fun setAdapter(list: List<Product>)
        fun setRecyclerViewLayoutManager()
        fun setRecyclerOnItemClickListener()
    }

    interface Presenter : BasePresenter {
        fun showProductList()
    }
}