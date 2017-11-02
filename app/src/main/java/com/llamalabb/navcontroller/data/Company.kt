package com.llamalabb.navcontroller.data

/**
 * Created by andy on 10/24/17.
 */
data class Company(
        val name: String,
        val domain: String,
        var logoURL: String = "https://logo.clearbit.com/$domain",
        var stockTicker: String? = null,
        var stockPrice: Double? = null,
        var productList: List<Product>? = null
)