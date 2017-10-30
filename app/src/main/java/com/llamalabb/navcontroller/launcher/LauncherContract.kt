package com.llamalabb.navcontroller.launcher

import com.llamalabb.navcontroller.BasePresenter
import com.llamalabb.navcontroller.BaseView

/**
 * Created by andy on 10/24/17.
 */
interface LauncherContract {
    interface View : BaseView<Presenter>{
        fun showCompanyFrag()
        fun showProductFrag()
    }

    interface Presenter : BasePresenter {

    }
}