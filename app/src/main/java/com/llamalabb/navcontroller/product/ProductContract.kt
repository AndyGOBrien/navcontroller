package com.llamalabb.navcontroller.product

import com.llamalabb.navcontroller.BasePresenter
import com.llamalabb.navcontroller.BaseView

/**
 * Created by andy on 10/24/17.
 */
interface ProductContract {
    interface View : BaseView<Presenter>{

    }

    interface Presenter : BasePresenter {

    }
}