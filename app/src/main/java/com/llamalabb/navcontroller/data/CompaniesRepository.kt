package com.llamalabb.navcontroller.data

import java.util.*
import kotlin.collections.LinkedHashMap

/**
 * Created by andy on 10/30/17.
 */
class CompaniesRepository(val companiesLocalDataSource: CompaniesDataSource) : CompaniesDataSource {

    var cachedCompanies: LinkedHashMap<String, Company> = LinkedHashMap()

    var cacheIsDirty = false


    override fun getCompanies(callback: CompaniesDataSource.LoadCompaniesCallback) {

        if(cachedCompanies.isNotEmpty() && !cacheIsDirty){
            callback.onCompaniesLoaded(ArrayList(cachedCompanies.values))
            return
        }

        companiesLocalDataSource.getCompanies(object: CompaniesDataSource.LoadCompaniesCallback {
            override fun onCompaniesLoaded(companies: List<Company>) {
                refreshCache(companies)
                callback.onCompaniesLoaded(ArrayList(cachedCompanies.values))
            }

            override fun onDataNotAvailable() {

                callback.onDataNotAvailable()

            }
        })
    }

    override fun getProducts(companyId: String, callback: CompaniesDataSource.LoadProductsCallback) {
        companiesLocalDataSource.getProducts(companyId,
                object: CompaniesDataSource.LoadProductsCallback {
            override fun onProductsLoaded(products: List<Product>) {
                callback.onProductsLoaded(products)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }

        })
    }

    override fun getCompany(companyId: String, callback: CompaniesDataSource.GetCompanyCallback) {

        val taskInCache = getCompanyWithId(companyId)

        if (taskInCache != null) {
            callback.onCompanyLoaded(taskInCache)
            return
        }

        companiesLocalDataSource.getCompany(companyId, object : CompaniesDataSource.GetCompanyCallback {
            override fun onCompanyLoaded(company: Company) {
                cacheAndPerform(company) {
                    callback.onCompanyLoaded(it)
                }
            }

            override fun onDataNotAvailable() {

            }
        })
    }

    override fun saveCompany(company: Company) {

        cacheAndPerform(company){
            companiesLocalDataSource.saveCompany(company)
        }

    }

    override fun deleteAllCompanies() {
        companiesLocalDataSource.deleteAllCompanies()
        cachedCompanies.clear()
    }

    override fun deleteCompany(companyId: String) {
        companiesLocalDataSource.deleteCompany(companyId)
        companiesLocalDataSource.deleteAllProducts(companyId)
        cachedCompanies.remove(companyId)
    }

    override fun deleteAllProducts(companyId: String) {
        companiesLocalDataSource.deleteAllProducts(companyId)
    }


    override fun saveProduct(product: Product) {
        companiesLocalDataSource.saveProduct(product)
    }

    override fun deleteProduct(productId: String) {
        companiesLocalDataSource.deleteProduct(productId)
    }

    private fun refreshCache(companies: List<Company>) {
        cachedCompanies.clear()
        companies.forEach {
            cacheAndPerform(it) {}
        }
        cacheIsDirty = false
    }

    private fun refreshLocalDataSource(companies: List<Company>) {
        companiesLocalDataSource.deleteAllCompanies()
        for (company in companies) {
            companiesLocalDataSource.saveCompany(company)
        }
    }

    private fun getCompanyWithId(id: String) = cachedCompanies[id]

    private inline fun cacheAndPerform(company: Company, perform: (Company) -> Unit) {
        val cachedCompany = Company(
                company.name,
                company.domain,
                id = company.id,
                logoURL = company.logoURL,
                stockManager = company.stockManager)

        cachedCompanies.put(cachedCompany.id, cachedCompany)
        perform(cachedCompany)
    }

    fun processStockData(
            company: Company,
            callBack: DataManagerCallBack,
            interval: String = "1min",
            function: String = "TIME_SERIES_INTRADAY"){

        company.stockManager.getStockData(callBack, interval, function)
    }

    interface DataManagerCallBack{
        fun onSuccess()
        fun onFailure()
    }

    companion object {

        private var INSTANCE: CompaniesRepository? = null

        @JvmStatic fun getInstance(companiesLocalDataSource: CompaniesDataSource): CompaniesRepository {
            return INSTANCE ?: CompaniesRepository(companiesLocalDataSource)
                    .apply { INSTANCE = this }
        }

        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }
    }


    private fun createDummyCompanies(){

        val companies = arrayOf(
                Company("Apple",
                        "apple.com",
                        initialStockTicker = "AAPL"),
                Company("Samsung",
                        "samsung.com",
                        null),
                Company("Motorola",
                        "motorola.com",
                        initialStockTicker = "MSI"),
                Company("Microsoft",
                        "microsoft.com",
                        initialStockTicker = "MSFT"),
                Company("Google",
                        "google.com",
                        initialStockTicker = "GOOG"))

        val strArrMatrix = arrayOf(
                arrayOf("iPhone", "iPad", "iPod"),
                arrayOf("Galaxy S8+", "Galaxy S7", "Note 8"),
                arrayOf("Moto E", "Moto G", "Moto X"),
                arrayOf("Lumia 540", "Lumia 640", "Lumia 925"),
                arrayOf("Nexus 6P", "Pixel", "Pixel XL", "Pixel 2", "Pixel XL2"))

        for (i in 0 until companies.size) {
            for (j in 0 until strArrMatrix[i].size) {
                saveProduct(Product(strArrMatrix[i][j], companies[i].id))
            }
            saveCompany(companies[i])
        }
    }
}