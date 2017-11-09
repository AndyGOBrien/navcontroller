package com.llamalabb.navcontroller.companies

import com.llamalabb.navcontroller.data.CompaniesDataSource
import com.llamalabb.navcontroller.data.Company
import com.llamalabb.navcontroller.data.CompaniesRepository

/**
 * Created by andy on 10/24/17.
 */
class CompaniesPresenter(val companiesView: CompaniesContract.View,
                         val companiesRepository: CompaniesRepository)
    : CompaniesContract.Presenter,
        CompaniesRepository.DataManagerCallBack{

    private var loadIndicator = 0

    override fun onFailure() {
        loadIndicator += 1
        if(loadIndicator >= companiesRepository.getCompanyList().size){
            companiesView.setLoadingIndicator(false)
            loadIndicator = 0
        }
    }
    override fun onSuccuss() {
        companiesView.showCompanies(companiesRepository.getCompanyList())
        loadIndicator += 1
        if(loadIndicator >= companiesRepository.getCompanyList().size) {
            companiesView.setLoadingIndicator(false)
            loadIndicator = 0
        }
    }

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

        //val companies = companiesRepository.getCompanyList()


        companiesRepository.getCompanies(object: CompaniesDataSource.LoadCompaniesCallback{
            override fun onCompaniesLoaded(companies: List<Company>) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataNotAvailable() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        //processCompanies(companies)
    }

    private fun processCompanies(companies: List<Company>){
        if(companies.isEmpty()) {
            companiesView.showNoCompanies()
        }
        else {
            for(company in companies) companiesRepository.processStockData(company, callBack = this)
            companiesView.showCompanies(companies)
        }
    }

    override fun setCompanyNum(position: Int) {
        companiesRepository.companyNum = position
    }


}