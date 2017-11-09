package com.llamalabb.navcontroller.data.stock

import android.util.Log
import com.llamalabb.navcontroller.data.CompaniesDataSource
import com.llamalabb.navcontroller.retrofit.ApiService
import com.llamalabb.navcontroller.retrofit.RetroClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by andy on 11/8/17.
 */
data class StockManager(
        val stockHistoricalData: StockHistoricalData = StockHistoricalData(),
        var stockTicker: String? = null,
        var stockPrice: String? = null) {

    fun getStockData(callBack: CompaniesDataSource.DataManagerCallBack,
                     interval: String,
                     function: String) {

        var data: Map<String, StockInformation>

        val api: ApiService = RetroClient.getStockApiService()

        val call: Call<StockDataMap> = api.getStockInfo(
                stockTicker,
                interval = interval,
                function = function)

        var retry = 0

        call.enqueue(object : Callback<StockDataMap> {
            override fun onFailure(call: Call<StockDataMap>, t: Throwable) {
                Log.d("STOCKMANAGER", "FAILURE")
                if (retry <= 20) {
                    call.clone().enqueue(this)
                } else {
                    callBack.onFailure()
                    retry = 0
                }
            }

            override fun onResponse(call: Call<StockDataMap>, response: Response<StockDataMap>) {
                if (response.isSuccessful) {
                    data = response.body().data
                    if (!data.isEmpty()) {
                        Log.d("INTRADAY 1", "SUCCESS!")
                        stockPrice = data.values.iterator().next().close
                        retry = 0
                        callBack.onSuccuss()
                    } else {
                        retry += 1
                        onFailure(call, Throwable("Empty List"))
                    }
                }
            }
        })
    }
}