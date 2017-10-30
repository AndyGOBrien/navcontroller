
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

class ProductFragment : Fragment(), ProductContract.View {
    lateinit var listener: ProductFragmentListener

    interface ProductFragmentListener{}

    override var presenter: ProductContract.Presenter = ProductPresenter(this)

    private lateinit var productRecyclerView: RecyclerView
    private lateinit var recyclerProductAdapter: ProductRecyclerAdapter
    lateinit var addButton: ImageButton
    lateinit var backButton: ImageButton

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        // Inflate the layout for this fragment
        val view = inflater!!.inflate(
                R.layout.vertical_base_item_list, container, false)

        productRecyclerView = view.findViewById(R.id.vertical_recycler_view)

        presenter.onStart()
        presenter.showProductList()

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



    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = context as ProductFragmentListener
    }

    override fun setAdapter(list: List<Product>) {

        recyclerProductAdapter = ProductRecyclerAdapter(context, list)
        productRecyclerView.adapter = recyclerProductAdapter

    }

    override fun setRecyclerViewLayoutManager() {

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        productRecyclerView.layoutManager = layoutManager

    }

    override fun setRecyclerOnItemClickListener() {
    }


}
