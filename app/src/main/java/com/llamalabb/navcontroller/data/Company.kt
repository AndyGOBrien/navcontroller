package com.llamalabb.navcontroller.data

import com.llamalabb.navcontroller.data.stock.StockManager
import java.util.*


/**
 * Created by andy on 10/24/17.
 */
data class Company(
        val name: String,
        val domain: String,
        val initialStockTicker: String? = null,
        val id: String = UUID.randomUUID().toString(),
        var logoURL: String = "https://logo.clearbit.com/$domain",
        val stockManager: StockManager = StockManager(
                stockTicker = initialStockTicker)
)