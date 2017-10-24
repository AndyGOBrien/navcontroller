package com.llamalabb.navcontroller.launcher

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.llamalabb.navcontroller.R
import com.llamalabb.navcontroller.company.CompanyFragment
import com.llamalabb.navcontroller.product.ProductFragment

class LauncherActivity :
        AppCompatActivity(),
        LauncherContract.View,
        CompanyFragment.CompanyFragmentListener,
        ProductFragment.ProductFragmentListener{
    override var presenter: LauncherContract.Presenter = LauncherPresenter(this)

    var companyNo: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        var fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainLayout, CompanyFragment()).commit()
    }

    override fun setCurrentCompanyNo(position: Int) { companyNo = position }


    override fun getCurrentCompanyNo() : Int{
        return companyNo
    }

}
