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


    init{
        companiesView.presenter = this
    }

    private var loadIndicator = 0

    private var firstLoad = true

    override fun onStart() {
        loadCompanies(firstLoad)
    }

    override fun loadCompanies(forceUpdate: Boolean) {
        loadCompanies(forceUpdate || firstLoad, true)
        firstLoad = false
    }

    private fun loadCompanies(forceUpdate: Boolean, showLoadingUI: Boolean){
        if(showLoadingUI) companiesView.setLoadingIndicator(true)

        companiesRepository.getCompanies(object: CompaniesDataSource.LoadCompaniesCallback{
            override fun onCompaniesLoaded(companies: List<Company>) {
                processCompanies(companies)
            }

            override fun onDataNotAvailable() {
                companiesView.showNoCompanies()
                companiesView.setLoadingIndicator(false)
            }
        })
    }

    private fun processCompanies(companies: List<Company>){
        if(companies.isEmpty()) {
            companiesView.showNoCompanies()
            companiesView.setLoadingIndicator(false)
        }
        else {
            for(company in companies) companiesRepository.processStockData(company, callBack = this)
            companiesView.showCompanies(companies)
        }
    }

    override fun openCompanyProducts(requestedCompany: Company) {
        companiesView.showProductsUi(requestedCompany.id)
    }

    override fun deleteCompany(requestedCompany: Company){
        with(companiesRepository) {
            deleteCompany(requestedCompany.id)
            processCompanies(ArrayList(cachedCompanies.values))
        }
    }

    override fun onFailure() {
        loadIndicator += 1
        if(loadIndicator >= companiesRepository.cachedCompanies.size){
            companiesView.setLoadingIndicator(false)
            loadIndicator = 0
        }
    }
    override fun onSuccess() {
        companiesView.showCompanies(ArrayList(companiesRepository.cachedCompanies.values))
        loadIndicator += 1
        if(loadIndicator >= companiesRepository.cachedCompanies.size) {
            companiesView.setLoadingIndicator(false)
            loadIndicator = 0
        }
    }
}