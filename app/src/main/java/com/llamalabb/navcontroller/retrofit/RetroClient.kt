package com.llamalabb.navcontroller.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by andy on 11/6/17.
 */
object RetroClient {

    private val STOCK_ROOT_URL = "https://www.alphavantage.co"
    private val POTENTIAL_COMPANY_INFO_ROOT_URL ="https://autocomplete.clearbit.com/"
    private val COMPANY_INFO_ROOT_URL = "https://company.clearbit.com/"

    private fun getStockRetrofitInstance() : Retrofit{
        return Retrofit.Builder()
                .baseUrl(STOCK_ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun getPotentialCompanyInfoRetrofitInstance() : Retrofit{
        return Retrofit.Builder()
                .baseUrl(POTENTIAL_COMPANY_INFO_ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun getCompanyInfoRetrofitInstance() : Retrofit{
        return Retrofit.Builder()
                .baseUrl(COMPANY_INFO_ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun getStockApiService() : ApiService = getStockRetrofitInstance().create(ApiService::class.java)
    fun getPotentialCompanyInfoApiService() : ApiService = getPotentialCompanyInfoRetrofitInstance().create(ApiService::class.java)
    fun getCompanyInfoApiService() : ApiService = getCompanyInfoRetrofitInstance().create(ApiService::class.java)


}