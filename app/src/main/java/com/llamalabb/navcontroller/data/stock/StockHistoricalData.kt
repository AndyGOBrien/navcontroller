package com.llamalabb.navcontroller.data.stock

/**
 * Created by andy on 11/6/17.
 */
data class StockHistoricalData(
        var oneMinInterval: LinkedHashMap<String, StockInformation>? = null,
        var fiveMinInterval: LinkedHashMap<String, StockInformation>? = null,
        var fifteenMinInterval: LinkedHashMap<String, StockInformation>? = null,
        var thirtyMinInterval: LinkedHashMap<String, StockInformation>? = null,
        var sixtyMinInterval: LinkedHashMap<String, StockInformation>? = null,
        var dailyInterval: LinkedHashMap<String, StockInformation>? = null,
        var weeklyInterval: LinkedHashMap<String, StockInformation>? = null,
        var monthlyInterval: LinkedHashMap<String, StockInformation>? = null
)