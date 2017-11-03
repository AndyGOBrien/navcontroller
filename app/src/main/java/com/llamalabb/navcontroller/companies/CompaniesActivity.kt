package com.llamalabb.navcontroller.companies

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.llamalabb.navcontroller.R
import com.llamalabb.navcontroller.companies.add.AddCompanyActivity
import com.llamalabb.navcontroller.products.ProductsActivity
import com.llamalabb.navcontroller.util.replaceFragmentInActivity
import kotlinx.android.synthetic.main.products_act.*


class CompaniesActivity : AppCompatActivity(), CompaniesFragment.CompanyFragmentListener{
    private lateinit var companiesPresenter: CompaniesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.companies_act)

        val companiesFragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
                as CompaniesFragment? ?: CompaniesFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.contentFrame)
        }

        companiesPresenter = CompaniesPresenter(companiesFragment).apply {
            savedInstanceState?.let{}
        }

        setClickListeners()
    }

    private fun setClickListeners(){
        val clickListener = View.OnClickListener {
            when (it) {
                fab_add_product -> fabClickAction()
            }
        }
        fab_add_product.setOnClickListener(clickListener)
    }

    private fun fabClickAction(){

        startActivity(Intent(this, AddCompanyActivity::class.java))

    }

    override fun loadProducts() {
        startActivity(Intent(this, ProductsActivity::class.java))
    }

}
