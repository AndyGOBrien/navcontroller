package com.llamalabb.navcontroller.companies

import com.llamalabb.navcontroller.data.Company
import com.llamalabb.navcontroller.data.DataManager

/**
 * Created by andy on 10/24/17.
 */
class CompaniesPresenter(val companiesView: CompaniesContract.View) :
        CompaniesContract.Presenter{

    private var firstLoad = true

    init{
        companiesView.presenter = this
    }

    override fun onStart() {
        loadCompanies(false)
    }

    override fun loadCompanies(forceUpdate: Boolean) {
        loadCompanies(forceUpdate || firstLoad, true)
        firstLoad = false
    }

    private fun loadCompanies(forceUpdate: Boolean, showLoadingUI: Boolean){
        if(showLoadingUI) companiesView.setLoadingIndicator(true)

        val companies = DataManager.getCompanyList()
        processCompanies(companies)

        companiesView.setLoadingIndicator(false)
    }

    private fun processCompanies(companies: List<Company>){
        if(companies.isEmpty())
            companiesView.showNoCompanies()
        else
            companiesView.showCompanies(companies)
    }

    override fun setCompanyNum(position: Int) {
        DataManager.companyNum = position
    }


}