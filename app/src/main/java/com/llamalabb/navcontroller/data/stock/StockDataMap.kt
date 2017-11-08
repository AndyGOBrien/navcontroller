package com.llamalabb.navcontroller.data.stock

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlin.collections.LinkedHashMap

/**
 * Created by andy on 11/7/17.
 */
data class StockDataMap(
        @SerializedName(value = "Time Series (1min)", alternate = arrayOf(
                    "Time Series (5min)",
                    "Time Series (15min)",
                    "Time Series (30min)",
                    "Time Series (60min)",
                    "Time Series (Daily)",
                    "Weekly Time Series",
                    "Monthly Time Series"))
        @Expose
        var data: LinkedHashMap<String, StockInformation> = LinkedHashMap()

)