package com.llamalabb.navcontroller.data.stock

/**
 * Created by andy on 11/6/17.
 */
data class StockHistoricalData(
        var oneMinInterval: Map<String, StockInformation>? = null,
        var fiveMinInterval: Map<String, StockInformation>? = null,
        var fifteenMinInterval: Map<String, StockInformation>? = null,
        var thirtyMinInterval: Map<String, StockInformation>? = null,
        var sixtyMinInterval: Map<String, StockInformation>? = null,
        var dailyInterval: Map<String, StockInformation>? = null,
        var weeklyInterval: Map<String, StockInformation>? = null,
        var monthlyInterval: Map<String, StockInformation>? = null
)

{
    fun setHistoricalData(data: Map<String, StockInformation>, interval: String, function: String){

        when(function) {
            "Time Series(Daily)" -> dailyInterval = data
            "Weekly Time Series" -> weeklyInterval = data
            "Monthly Time Series" -> monthlyInterval = data
            else ->
                when(interval){
                    "Time Series(1min)" -> oneMinInterval = data
                    "Time Series(5min)" -> fiveMinInterval = data
                    "Time Series(15min)" -> fifteenMinInterval = data
                    "Time Series(30min)" -> thirtyMinInterval = data
                    "Time Series(60min)" -> sixtyMinInterval = data
                }
        }
    }
}