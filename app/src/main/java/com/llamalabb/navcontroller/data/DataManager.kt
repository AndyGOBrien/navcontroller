package com.llamalabb.navcontroller.data

import android.util.Log
import com.llamalabb.navcontroller.data.DataManager.addCompany
import com.llamalabb.navcontroller.data.stock.StockDataMap
import com.llamalabb.navcontroller.data.stock.StockInformation
import com.llamalabb.navcontroller.retrofit.ApiService
import com.llamalabb.navcontroller.retrofit.RetroClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Created by andy on 10/30/17.
 */
object DataManager {

    private var companyList: ArrayList<Company> = ArrayList()

    var companyNum: Int

    init{
        createDummyCompanyList()
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

    fun addCompanyProduct(product: Product){
        companyList[companyNum].productList.add(product)
    }

    fun getStockData(company: Company,
                     interval: String = "1min",
                     function: String = "TIME_SERIES_INTRADAY",
                     callBack: DataManagerCallBack){

        var data: Map<String, StockInformation>

        val api: ApiService = RetroClient.getApiService()

        val call: Call<StockDataMap> = api.getStockInfo(
                company.stockTicker,
                interval = interval,
                function = function)

        var retry = 0

        call.enqueue(object : Callback<StockDataMap> {
            override fun onFailure(call: Call<StockDataMap>, t: Throwable) {
                Log.d("INTRADAY 1", "FAILURE")
                if(retry <= 10) {
                    call.clone().enqueue(this)
                } else {
                    callBack.onFailure()
                    retry = 0
                }
            }

            override fun onResponse(call: Call<StockDataMap>, response: Response<StockDataMap>) {
                if(response.isSuccessful){
                    data = response.body().data
                    if(!data.isEmpty()) {
                        Log.d("INTRADAY 1", "SUCCESS!")
                        company.stockPrice = data.values.iterator().next().close
                        callBack.onSuccuss()
                    } else {
                        retry += 1
                        onFailure(call, Throwable("Empty List"))
                    }
                }
            }
        })
    }

    private fun createDummyCompanyList(){
        addCompany(Company(
                "Apple",
                "apple.com",
                stockTicker = "AAPL",
                productList = dummyAppleProducts()))

        addCompany(Company(
                "Samsung",
                "samsung.com",
                stockTicker = "MSFT",
                productList = dummySamsungProducts()))

        addCompany(Company(
                "Motorola",
                "motorola.com",
                stockTicker = "MSI",
                productList = dummyMotorolaProducts()))

        addCompany(Company(
                "Microsoft",
                "microsoft.com",
                stockTicker = "MSFT",
                productList = dummyMicrosoftProducts()))

        addCompany(Company(
                "Google",
                "google.com",
                stockTicker = "GOOG",
                productList = dummyGoogleProducts()))
    }

    private fun dummyAppleProducts(): ArrayList<Product>{
        var list: ArrayList<Product> = ArrayList()

        list.add(Product("Iphone"))
        list.add(Product("IPad"))
        list.add(Product("IPod"))

        return list

    }

    private fun dummySamsungProducts(): ArrayList<Product>{
        var list: ArrayList<Product> = ArrayList()
        list.add(Product("Galaxy s"))
        list.add(Product("Galaxy Note"))
        list.add(Product("J7"))

        return list
    }

    private fun dummyMotorolaProducts(): ArrayList<Product>{
        var list: ArrayList<Product> = ArrayList()
        list.add(Product("Moto E"))
        list.add(Product("Moto G"))
        list.add(Product("Moto X"))

        return list
    }

    private fun dummyMicrosoftProducts(): ArrayList<Product>{
        var list: ArrayList<Product> = ArrayList()
        list.add(Product("Lumia 540"))
        list.add(Product("Lumia 640"))
        list.add(Product("Lumia 925"))

        return list
    }

    private fun dummyGoogleProducts(): ArrayList<Product>{
        var list: ArrayList<Product> = ArrayList()
        list.add(Product("Nexus 6P"))
        list.add(Product("Pixel"))
        list.add(Product("Pixel XL"))
        list.add(Product("Pixel 2"))
        list.add(Product("Pixel XL2"))

        return list
    }
}