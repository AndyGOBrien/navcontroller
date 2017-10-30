package com.llamalabb.navcontroller.product

import com.llamalabb.navcontroller.data.DataManager.getCompanyProductList

/**
 * Created by andy on 10/24/17.
 */
class ProductPresenter(val view: ProductContract.View) : ProductContract.Presenter{
    override fun onStart() {
    }

    override fun showProductList() {
        view.setRecyclerViewLayoutManager()
        view.setAdapter(getCompanyProductList())
        view.setRecyclerOnItemClickListener()
    }

}