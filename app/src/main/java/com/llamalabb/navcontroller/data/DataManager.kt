package com.llamalabb.navcontroller.data

import java.util.*


/**
 * Created by andy on 10/30/17.
 */
object DataManager {

    private var companyList: ArrayList<Company> = ArrayList()

    var companyNum: Int

    init{
        createDummyCompanyList()
        companyNum = 0
    }


    fun addCompany(company: Company){
        companyList.add(company)
    }

    fun getCompanyList() : ArrayList<Company>{
        return companyList
    }

    fun clearCompanyList(){
        companyList.clear()
    }

    fun getCompanyProductList(): List<Product>{
        return companyList[companyNum].productList
    }

    private fun createDummyCompanyList(){
        DataManager.addCompany(Company("Apple", "apple.com", dummyAppleProducts()))
        DataManager.addCompany(Company("Samsung", "samsung.com", dummySamsungProducts()))
        DataManager.addCompany(Company("Motorola", "motorola.com", dummyMotorolaProducts()))
        DataManager.addCompany(Company("Microsoft","microsoft.com", dummyMicrosoftProducts()))
        DataManager.addCompany(Company("Google", "google.com", dummyGoogleProducts()))
    }

    private fun dummyAppleProducts(): List<Product>{
        var list: ArrayList<Product> = ArrayList()

        list.add(Product("Iphone"))
        list.add(Product("IPad"))
        list.add(Product("IPod"))

        return list

    }

    private fun dummySamsungProducts(): List<Product>{
        var list: ArrayList<Product> = ArrayList()
        list.add(Product("Galaxy s"))
        list.add(Product("Galaxy Note"))
        list.add(Product("J7"))

        return list
    }

    private fun dummyMotorolaProducts(): List<Product>{
        var list: ArrayList<Product> = ArrayList()
        list.add(Product("Moto E"))
        list.add(Product("Moto G"))
        list.add(Product("Moto X"))

        return list
    }

    private fun dummyMicrosoftProducts(): List<Product>{
        var list: ArrayList<Product> = ArrayList()
        list.add(Product("Lumia 540"))
        list.add(Product("Lumia 640"))
        list.add(Product("Lumia 925"))

        return list
    }

    private fun dummyGoogleProducts(): List<Product>{
        var list: ArrayList<Product> = ArrayList()
        list.add(Product("Nexus 6P"))
        list.add(Product("Pixel"))
        list.add(Product("Pixel XL"))
        list.add(Product("Pixel 2"))
        list.add(Product("Pixel XL2"))

        return list
    }
}