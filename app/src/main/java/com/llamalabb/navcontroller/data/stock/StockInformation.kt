package com.llamalabb.navcontroller.data.stock

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by andy on 11/6/17.
 */
data class StockInformation(
        @SerializedName("1. open")
        @Expose
        var open: String? = null,

        @SerializedName("2. high")
        @Expose
        var high: String? = null,

        @SerializedName("3. low")
        @Expose
        var low: String? = null,

        @SerializedName("4. close")
        @Expose
        var close: String? = null,

        @SerializedName("5. volume")
        @Expose
        var volume: String? = null
)