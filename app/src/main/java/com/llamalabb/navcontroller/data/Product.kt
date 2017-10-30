package com.llamalabb.navcontroller.data

/**
 * Created by andy on 10/24/17.
 */
class Product(val name: String){

    var companyID: Int = 0
    var id: Int = 0
    lateinit var logoURL: String
    lateinit var productURL: String

    constructor(name: String, companyID: Int) : this(name) {
        this.companyID = companyID
    }

    constructor(name: String, companyID: Int, logoURL: String)
            : this(name, companyID){

        this.logoURL = logoURL
    }

    constructor(name: String, companyID: Int, logoURL: String, productURL: String)
            : this(name, companyID, logoURL){

        this.productURL = productURL
    }

}