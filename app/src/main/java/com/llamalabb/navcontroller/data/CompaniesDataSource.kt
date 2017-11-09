package com.llamalabb.navcontroller.data

interface CompaniesDataSource{

    interface LoadCompaniesCallback {

        fun onCompaniesLoaded(companies: List<Company>)

        fun onDataNotAvailable()

    }

    interface GetCompanyCallback{

        fun onCompanyLoaded(company: Company)

        fun onDataNotAvailable()

    }

    fun getCompanies(callback: LoadCompaniesCallback)

    fun getCompany(companyId: String, callback: GetCompanyCallback)

    fun saveCompany(company: Company)

    fun deleteAllCompanies()

    fun deleteCompany(companyId: String)
}