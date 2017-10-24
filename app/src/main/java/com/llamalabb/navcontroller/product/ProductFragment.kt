
package com.llamalabb.navcontroller.product

import android.content.Context
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton

import com.llamalabb.navcontroller.R
import com.llamalabb.navcontroller.data.Product


import java.util.ArrayList

class ProductFragment : Fragment(), ProductContract.View {

    lateinit var listener: ProductFragmentListener

    interface ProductFragmentListener{
        fun getCurrentCompanyNo() : Int
    }

    override var presenter: ProductContract.Presenter = ProductPresenter(this)


    private lateinit var product_recycler_view: RecyclerView
    private lateinit var appleProducts: ArrayList<Product>
    private lateinit var samsungProducts:ArrayList<Product>
    private lateinit var motorolaProducts:ArrayList<Product>
    private lateinit var microsoftProducts:ArrayList<Product>
    private lateinit var recyclerProductAdapter: ProductRecyclerAdapter
    lateinit var listOfAllProducts: MutableList<List<Product>>
    lateinit var addButton: ImageButton
    lateinit var backButton: ImageButton



    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = context as ProductFragmentListener
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater!!.inflate(
                R.layout.vertical_base_item_list, container, false)

        product_recycler_view = view.findViewById(R.id.vertical_recycler_view)

        // Get the Company to display correct Products
        val companyNo = listener.getCurrentCompanyNo()

        listOfAllProducts = ArrayList()

        // apple Products
        appleProducts = ArrayList()
        appleProducts.add(Product("Iphone"))
        appleProducts.add(Product("IPad"))
        appleProducts.add(Product("IPod"))

        // Samsung Products
        samsungProducts = ArrayList()
        samsungProducts.add(Product("Galaxy s"))
        samsungProducts.add(Product("Galaxy Note"))
        samsungProducts.add(Product("J7"))

        // Moto Products
        motorolaProducts = ArrayList()
        motorolaProducts.add(Product("Moto E"))
        motorolaProducts.add(Product("Moto G"))
        motorolaProducts.add(Product("Moto X"))

        // Microsoft Products
        microsoftProducts = ArrayList()
        microsoftProducts.add(Product("Lumia 540"))
        microsoftProducts.add(Product("Lumia 640"))
        microsoftProducts.add(Product("Lumia 925"))

        // add all products to the main list
        listOfAllProducts.add(appleProducts)
        listOfAllProducts.add(samsungProducts)
        listOfAllProducts.add(motorolaProducts)
        listOfAllProducts.add(microsoftProducts)

        recyclerProductAdapter = ProductRecyclerAdapter(context, listOfAllProducts[companyNo])


        val layoutmanager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        product_recycler_view.layoutManager = layoutmanager


        product_recycler_view.adapter = recyclerProductAdapter


//        // ActionBar SetUp
//        val activity = getActivity() as AppCompatActivity
//        val actionBar = activity.supportActionBar
//        actionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
//        actionBar.setCustomView(R.layout.toolbar)
//        backButton = activity.findViewById(R.id.imageButton) as ImageButton
//        addButton = activity.findViewById(R.id.imageButton2) as ImageButton
//
//        backButton.setOnClickListener { Toast.makeText(getContext(), "Back", Toast.LENGTH_LONG).show() }
//
//        addButton.setOnClickListener { Toast.makeText(getContext(), "Add", Toast.LENGTH_LONG).show() }




        return view
    }
}