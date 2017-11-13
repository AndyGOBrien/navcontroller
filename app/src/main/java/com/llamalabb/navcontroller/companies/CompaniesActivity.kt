package com.llamalabb.navcontroller.companies

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.llamalabb.navcontroller.R
import com.llamalabb.navcontroller.companies.add.AddCompanyActivity
import com.llamalabb.navcontroller.data.CompaniesRepository
import com.llamalabb.navcontroller.data.source.CompaniesLocalDataSource
import com.llamalabb.navcontroller.util.replaceFragmentInActivity
import kotlinx.android.synthetic.main.products_act.*


class CompaniesActivity : AppCompatActivity(){
    private lateinit var companiesPresenter: CompaniesPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.companies_act)

        val companiesFragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
                as CompaniesFragment? ?: CompaniesFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.contentFrame)
        }

        // Create the presenter
        companiesPresenter = CompaniesPresenter(companiesFragment, CompaniesRepository.getInstance(
                        CompaniesLocalDataSource.getInstance(this))).apply{
            // Load previously saved state, if available.
            if (savedInstanceState != null) {

            }
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

}
