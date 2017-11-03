package com.llamalabb.navcontroller.companies.add

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

import com.llamalabb.navcontroller.R
import com.llamalabb.navcontroller.data.Company
import com.llamalabb.navcontroller.data.DataManager
import com.llamalabb.navcontroller.products.ProductsActivity
import kotlinx.android.synthetic.main.add_company_act.*

/**
 * Created by andy on 11/2/17.
 */
class AddCompanyActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_company_act)
        setClickListeners()
    }

    private fun addButtonAction(){
        val companyName = company_name_editText.text.toString()
        val companyDomain = company_domain_editText.text.toString()
        DataManager.addCompany(Company(companyName, companyDomain))

        DataManager.companyNum = DataManager.getCompanyList().lastIndex
        startActivity(Intent(this, ProductsActivity::class.java))
        finish()
    }

    private fun setClickListeners(){
        val clickListener = View.OnClickListener {
            when (it) {
                add_button -> addButtonAction()
            }
        }
        add_button.setOnClickListener(clickListener)
    }
}