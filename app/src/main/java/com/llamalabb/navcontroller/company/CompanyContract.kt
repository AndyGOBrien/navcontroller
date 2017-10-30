package com.llamalabb.navcontroller.company

import com.llamalabb.navcontroller.BasePresenter
import com.llamalabb.navcontroller.BaseView
import com.llamalabb.navcontroller.data.Company

/**
 * Created by andy on 10/24/17.
 */
interface CompanyContract {

    interface View : BaseView<Presenter>{
        fun setAdapter(list: List<Company>)
        fun setRecyclerViewLayoutManager()
        fun setRecyclerOnItemClickListener()
    }

    interface Presenter : BasePresenter {
        fun showCompanyList()
        fun setCompanyNum(position: Int)
    }

}