package com.llamalabb.navcontroller.companies

import com.llamalabb.navcontroller.BasePresenter
import com.llamalabb.navcontroller.BaseView
import com.llamalabb.navcontroller.data.Company

/**
 * Created by andy on 10/24/17.
 */
interface CompaniesContract {

    interface View : BaseView<Presenter>{
        fun showCompanies(list: List<Company>)
        fun showNoCompanies()
        fun setLoadingIndicator(active: Boolean)
        fun showProductsUi(companyId: String)
    }

    interface Presenter : BasePresenter {
        fun openCompanyProducts(requestedCompany: Company)
        fun loadCompanies(forceUpdate: Boolean)
        fun deleteCompany(requestedCompany: Company)
    }

}