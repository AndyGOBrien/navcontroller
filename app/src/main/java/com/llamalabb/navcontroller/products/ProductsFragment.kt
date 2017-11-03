
package com.llamalabb.navcontroller.products

import android.content.Context
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView

import com.llamalabb.navcontroller.R
import com.llamalabb.navcontroller.data.Product
import com.llamalabb.navcontroller.utils.Utils
import kotlinx.android.synthetic.main.products_frag.*
import kotlinx.android.synthetic.main.products_frag.view.*

class ProductsFragment : Fragment(), ProductsContract.View {

    override lateinit var presenter: ProductsContract.Presenter

    lateinit var listener: ProductFragmentListener
    private val recyclerAdapter = ProductsAdapter(ArrayList(0))


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.products_frag, container, false)

        with(root){
            val recyclerView = products_recyclerView.apply{
                adapter = recyclerAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                setRecyclerOnItemClickListener(this)
            }

            refresh_layout.apply{
                scrollUpChild = recyclerView
                setOnRefreshListener { presenter.loadProducts(false) }
            }
        }

        return root
    }

    override fun setLoadingIndicator(active: Boolean) {
        val root = view ?: return
        with(root.findViewById<SwipeRefreshLayout>(R.id.refresh_layout)) {
            post { isRefreshing = active }
        }
    }

    override fun showProducts(companyName: String, list: List<Product>) {
        recyclerAdapter.products = list
        company_display_textView.text = "$companyName's Product List"
        products_layout.visibility = View.VISIBLE
        no_products_layout.visibility = View.GONE

    }

    override fun showNoProducts(companyName: String){
        company_display_textView.text = "$companyName's Product List"
        products_layout.visibility = View.GONE
        no_products_layout.visibility = View.VISIBLE
    }

    private fun setRecyclerOnItemClickListener(recyclerView: RecyclerView){
        //TODO: implement listener for recycler items. each item should go to product page
    }

    private class ProductsAdapter(products: List<Product>)
        : RecyclerView.Adapter<ProductsAdapter.MyViewHolder>() {

        var products: List<Product> = products
            set(products){
                field = products
                notifyDataSetChanged()
            }

        class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            var textProductName: TextView = view.findViewById(R.id.textProductName)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
                ProductsAdapter.MyViewHolder {

            val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.product_item, parent, false)

            return ProductsAdapter.MyViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

            val name = products[position].name

            holder.textProductName.apply {
                text = name
                setOnClickListener { Utils.showMessageShort(msg = name) }
            }

        }

        override fun getItemCount(): Int {
            return products.size
        }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = context as ProductFragmentListener
    }

    override fun onResume() {
        super.onResume()
        presenter.onStart()
    }

    interface ProductFragmentListener{

    }

    companion object {
        fun newInstance() = ProductsFragment()
    }

}
