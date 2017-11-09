package com.llamalabb.navcontroller.data

import java.util.*

/**
 * Created by andy on 10/30/17.
 */
class CompaniesRepository(val companiesLocalDataSource: CompaniesDataSource) : CompaniesDataSource {


    override fun getCompanies(callback: CompaniesDataSource.LoadCompaniesCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCompany(companyId: String, callback: CompaniesDataSource.GetCompanyCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveCompany(company: Company) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAllCompanies() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteCompany(companyId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var companyList: ArrayList<Company> = ArrayList()

    var companyNum: Int

    init{
        createDummyCompanies()
        companyNum = 0
    }

    interface DataManagerCallBack{
        fun onSuccuss()
        fun onFailure()
    }


    fun addCompany(company: Company){
        companyList.add(company)
    }

    fun getCompanyList() : ArrayList<Company>{
        return companyList
    }

    fun clearCompanyList(){
        companyList.clear()
    }

    fun getCompanyProductList(): List<Product>{
        return companyList[companyNum].productList
    }

    fun addCompanyProduct(productName: String){
        companyList[companyNum].let {
            it.productList.add(Product(productName, it.id))
        }
    }

    fun processStockData(
            company: Company,
            callBack: DataManagerCallBack,
            interval: String = "1min",
            function: String = "TIME_SERIES_INTRADAY"){

        company.stockManager.getStockData(callBack, interval, function)
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
                companies[i].productList.add(Product(strArrMatrix[i][j], companies[i].id))
            }
            companyList.add(companies[i])
        }
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
}