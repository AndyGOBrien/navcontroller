package com.llamalabb.navcontroller.data

import java.time.temporal.TemporalAdjusters.next

/**
 * Created by andy on 10/24/17.
 */
data class Company(val name: String,
                   val domain: String,
                   var logoURL: String = "https://logo.clearbit.com/$domain",
                   var stockTicker: String? = null,
                   var stockPrice: String? = null,
                   var productList: ArrayList<Product> = ArrayList()
)