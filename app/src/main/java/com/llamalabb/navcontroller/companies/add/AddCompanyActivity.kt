package com.llamalabb.navcontroller.companies.add

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView

import com.llamalabb.navcontroller.R
import com.llamalabb.navcontroller.companies.PotentialCompany
import com.llamalabb.navcontroller.data.Company
import com.llamalabb.navcontroller.data.CompaniesRepository
import com.llamalabb.navcontroller.data.source.CompaniesLocalDataSource
import com.llamalabb.navcontroller.products.ProductsActivity
import com.llamalabb.navcontroller.retrofit.RetroClient
import kotlinx.android.synthetic.main.add_company_act.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by andy on 11/2/17.
 */
class AddCompanyActivity : AppCompatActivity() {

    private lateinit var companiesRepository: CompaniesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_company_act)
        setClickListeners()
        companyEditTextListener()

        companiesRepository = CompaniesRepository.getInstance(
                        CompaniesLocalDataSource.getInstance(this))
    }

    private fun addButtonAction() {

        val companyName = company_name_editText.text.toString()
        val companyDomain = company_domain_editText.text.toString()
        val initialStockTicker = stock_ticker_editText.text.toString()
        val company = Company(companyName, companyDomain,
                initialStockTicker.let{ if (it.isBlank()) null else it })

        companiesRepository.saveCompany(company)

        startActivity(Intent(this, ProductsActivity::class.java).apply{
            putExtra(ProductsActivity.COMPANY_ID, company.id)
        })

        finish()
    }

    private fun setClickListeners() {
        val clickListener = View.OnClickListener {
            when (it) {
                add_button -> addButtonAction()
            }
        }
        add_button.setOnClickListener(clickListener)
    }

    private fun companyEditTextListener() {
        val api = RetroClient.getPotentialCompanyInfoApiService()
        val api2 = RetroClient.getCompanyInfoApiService()

        val companyName = findViewById<AutoCompleteTextView>(R.id.company_name_editText)
        val nameList = ArrayList<String>()
        val adapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, nameList)

        company_name_editText.setOnItemClickListener { _, _, _, _ ->
            val callForDomain: Call<PotentialCompany> =
                    api2.getCompanyDomain(name = company_name_editText.text.toString())

            callForDomain.enqueue(object: Callback<PotentialCompany>{
                override fun onResponse(call: Call<PotentialCompany>?, response: Response<PotentialCompany>?) {
                    company_domain_editText.setText(response?.body()?.domain)
                }

                override fun onFailure(call: Call<PotentialCompany>?, t: Throwable?) {
                }
            })
        }

        var call: Call<ArrayList<PotentialCompany>>
        companyName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                company_domain_editText.setText("")
                call = api.getCompanyInfo(p0.toString())
                call.enqueue(object : Callback<ArrayList<PotentialCompany>> {
                    override fun onResponse(call: Call<ArrayList<PotentialCompany>>, response: Response<ArrayList<PotentialCompany>>?) {
                        if (response?.body()?.isNotEmpty() == true) {
                            response.body().mapTo(nameList) { it.name }
                            val temp = nameList.distinct()
                            nameList.clear()
                            temp.mapTo(nameList) { it }
                            adapter.clear()
                            adapter.addAll(nameList)
                            companyName.setAdapter(adapter)
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<PotentialCompany>>, t: Throwable?) {

                    }
                })
            }
        })
    }
}