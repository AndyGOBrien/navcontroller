package com.llamalabb.navcontroller.data

import java.util.*
import java.util.UUID.randomUUID

/**
 * Created by andy on 10/24/17.
 */
class Company(val name: String, val domain: String) {

    val logoURL = "https://logo.clearbit.com/" + domain
    val id: UUID = randomUUID()

    var stock_price: Double = 0.0
    lateinit var stock_ticker: String

    lateinit var productList: List<Product>

    constructor(name: String, domain: String, productList: List<Product>)
            : this(name, domain){

        this.productList = productList
    }

}