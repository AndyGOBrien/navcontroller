package com.llamalabb.navcontroller.data

import java.util.*

/**
 * Created by andy on 10/24/17.
 */
data class Product(
        val name: String,
        var companyID: String,
        var logoURL: String? = null,
        var productURL: String? = null,
        var id: String = UUID.randomUUID().toString()
)