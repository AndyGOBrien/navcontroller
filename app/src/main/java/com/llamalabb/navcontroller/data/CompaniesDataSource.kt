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

    interface LoadProductsCallback{
        fun onProductsLoaded(products: List<Product>)
        fun onDataNotAvailable()
    }

    fun getCompanies(callback: LoadCompaniesCallback)

    fun getCompany(companyId: String, callback: GetCompanyCallback)

    fun saveCompany(company: Company)

    fun deleteAllCompanies()

    fun deleteCompany(companyId: String)

    fun getProducts(companyId: String, callback: LoadProductsCallback)

    fun saveProduct(product: Product)

    fun deleteProduct(productId: String)

    fun deleteAllProducts(companyId: String)
}