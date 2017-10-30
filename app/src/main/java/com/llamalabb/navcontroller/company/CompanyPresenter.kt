package com.llamalabb.navcontroller.company

import com.llamalabb.navcontroller.data.Company
import com.llamalabb.navcontroller.data.DataManager

/**
 * Created by andy on 10/24/17.
 */
class CompanyPresenter(val view: CompanyContract.View) :
        CompanyContract.Presenter{

    override fun onStart() {

    }

    override fun showCompanyList() {
        view.setRecyclerViewLayoutManager()
        view.setAdapter(DataManager.getCompanyList())
        view.setRecyclerOnItemClickListener()
    }

    override fun setCompanyNum(position: Int) {
        DataManager.companyNum = position
    }

}