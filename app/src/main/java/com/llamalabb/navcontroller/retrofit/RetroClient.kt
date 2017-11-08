package com.llamalabb.navcontroller.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by andy on 11/6/17.
 */
object RetroClient {

    private val ROOT_URL = "https://www.alphavantage.co"

    private fun getRetrofitInstance() : Retrofit{
        return Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun getApiService() : ApiService = getRetrofitInstance().create(ApiService::class.java)

}