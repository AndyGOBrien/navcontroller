package com.llamalabb.navcontroller.companies

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.llamalabb.navcontroller.R
import com.llamalabb.navcontroller.data.Company
import com.llamalabb.navcontroller.products.ProductsActivity
import com.llamalabb.navcontroller.util.loadImage
import com.llamalabb.navcontroller.utils.Utils

import kotlinx.android.synthetic.main.companies_frag.*
import kotlinx.android.synthetic.main.companies_frag.view.*

/**
 * Created by andy on 10/24/17.
 */
class CompaniesFragment : Fragment(), CompaniesContract.View{

    override lateinit var presenter: CompaniesContract.Presenter

    private var itemListener: CompanyItemListener = object : CompanyItemListener {
        override fun onCompanyClick(clickedCompany: Company) {
            presenter.openCompanyProducts(clickedCompany)
        }

        override fun onCompanyLongPress(longClickedCompany: Company) {
            Utils.showMessageShort(context, "Company Deleted")
            presenter.deleteCompany(longClickedCompany)
        }

    }

    private val recyclerAdapter = CompaniesRecyclerAdapter(ArrayList(0), itemListener)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.companies_frag, container,false)

        with(root) {
            val recyclerView = (companies_recyclerView as RecyclerView).apply {
                adapter = recyclerAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }

            refresh_layout.apply {
                scrollUpChild = recyclerView
                setOnRefreshListener { presenter.loadCompanies(true) }
            }
        }

        return root

    }

    override fun setLoadingIndicator(active: Boolean) {
        val root = view ?: return
        with(root.findViewById<SwipeRefreshLayout>(R.id.refresh_layout)) {
            // Make sure setRefreshing() is called after the layout is done with everything else.
            post { isRefreshing = active }
        }
    }

    override fun showCompanies(list: List<Company>) {
        recyclerAdapter.companies = list
        companies_layout.visibility = View.VISIBLE
        no_companies_layout.visibility = View.GONE
    }

    override fun showNoCompanies() {
        companies_layout.visibility = View.GONE
        no_companies_layout.visibility = View.VISIBLE
    }

    override fun showProductsUi(companyId: String) {
        val intent = Intent(context, ProductsActivity::class.java).apply{
            putExtra(ProductsActivity.COMPANY_ID, companyId)
        }
        startActivity(intent)
    }

    private class CompaniesRecyclerAdapter(companies: List<Company>, private val itemListener: CompanyItemListener) :
            RecyclerView.Adapter<CompaniesRecyclerAdapter.MyViewHolder>(){

        var companies: List<Company> = companies
            set(companies){
                field = companies
                notifyDataSetChanged()
            }

        class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            var txtView: TextView = view.findViewById(R.id.txtView)
            var logoImg: ImageView = view.findViewById(R.id.product_logo_image)
            var stockTickerText: TextView = view.findViewById(R.id.stock_ticker_text)
            var stockPriceText: TextView = view.findViewById(R.id.stock_price_text)
            var companyView: CardView = view.findViewById(R.id.company_card_view)

        }
        override fun getItemCount(): Int {return companies.size}

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val company = companies[position]
            val stockManager = company.stockManager

            holder.apply{
                txtView.text = company.name
                stockTickerText.text = stockManager.stockTicker?.let{"$it: "}
                stockPriceText.text = stockManager.stockPrice?.let{"$$it"}
                logoImg.loadImage(company.logoURL)
                companyView.setOnClickListener{ itemListener.onCompanyClick(company) }
                companyView.setOnLongClickListener {
                    itemListener.onCompanyLongPress(company)
                    true
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : MyViewHolder{

            var itemView: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.company_item, parent, false)

            return MyViewHolder(itemView)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.onStart()
    }

    companion object{
        fun newInstance() = CompaniesFragment()
    }

    interface CompanyItemListener {
        fun onCompanyClick(clickedCompany: Company)
        fun onCompanyLongPress(longPressedCompany: Company)
    }

}
