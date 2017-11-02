package com.llamalabb.navcontroller.data

/**
 * Created by andy on 10/24/17.
 */
data class Product(
        val name: String,
        var companyID: Int? = null,
        var logoURL: String? = null,
        var productURL: String? = null,
        var id: Int? = null
)