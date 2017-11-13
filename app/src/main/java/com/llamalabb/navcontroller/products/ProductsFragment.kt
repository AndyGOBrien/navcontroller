
package com.llamalabb.navcontroller.products

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageView
import android.widget.TextView

import com.llamalabb.navcontroller.R
import com.llamalabb.navcontroller.data.Product
import com.llamalabb.navcontroller.util.loadImage
import kotlinx.android.synthetic.main.products_frag.*
import kotlinx.android.synthetic.main.products_frag.view.*

class ProductsFragment : Fragment(), ProductsContract.View {

    override lateinit var presenter: ProductsContract.Presenter

    private val itemListener = object: ProductItemListener{
        override fun onProductClick(productClicked: Product) {
            presenter.openProductPage(productClicked)
        }

        override fun onProductLongClick(productLongClicked: Product) {
            presenter.deleteProduct(productLongClicked)
        }
    }

    private val recyclerAdapter = ProductsAdapter(ArrayList(0), itemListener)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.products_frag, container, false)

        with(root){
            val recyclerView = products_recyclerView.apply{
                adapter = recyclerAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
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

    override fun showProductPageUi(productUrl: String?) {

        productUrl?.let{
            val uri = Uri.parse(it)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

    }

    private class ProductsAdapter(products: List<Product>, private val itemListener: ProductItemListener)
        : RecyclerView.Adapter<ProductsAdapter.MyViewHolder>() {

        var products: List<Product> = products
            set(products){
                field = products
                notifyDataSetChanged()
            }

        class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            var textProductName: TextView = view.findViewById(R.id.textProductName)
            var productView: CardView = view.findViewById(R.id.product_card_view)
            var productLogo: ImageView = view.findViewById(R.id.product_logo_image)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
                ProductsAdapter.MyViewHolder {

            val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.product_item, parent, false)

            return ProductsAdapter.MyViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

            val product = products[position]
            val name = product.name
            val logoUrl = product.logoURL

            holder.apply {
                textProductName.text = name
                logoUrl?.let{productLogo.loadImage(it)}
                productView.setOnClickListener { itemListener.onProductClick(product) }
                productView.setOnLongClickListener {
                    itemListener.onProductLongClick(product)
                    true
                }
            }

        }

        override fun getItemCount(): Int {
            return products.size
        }

    }

    override fun onResume() {
        super.onResume()
        presenter.onStart()
    }

    interface ProductItemListener{
        fun onProductClick(productClicked: Product)
        fun onProductLongClick(productLongClicked: Product)
    }

    companion object {
        fun newInstance() = ProductsFragment()
    }

}
