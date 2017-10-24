package com.llamalabb.navcontroller.company

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.llamalabb.navcontroller.R
import com.llamalabb.navcontroller.data.Company
import com.llamalabb.navcontroller.product.ProductFragment
import com.llamalabb.navcontroller.utils.Utils

/**
 * Created by andy on 10/24/17.
 */
class CompanyFragment : Fragment(),
        CompanyContract.View,
        View.OnClickListener{

    lateinit var listener: CompanyFragmentListener

    interface CompanyFragmentListener {
        fun setCurrentCompanyNo(position :Int)
    }

    override var presenter: CompanyContract.Presenter = CompanyPresenter(this)

    private lateinit var recyclerView: RecyclerView
    private lateinit var listOfCompany: ArrayList<Company>
    private lateinit var recyclerAdapter: CompanyRecyclerAdapter
    private lateinit var addButton: ImageButton
    private lateinit var backButton: ImageButton

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        presenter.onStart()
        var view: View = inflater!!.inflate(
                R.layout.vertical_base_item_list, container,false)

        recyclerView = view.findViewById(R.id.vertical_recycler_view)


        listOfCompany = ArrayList()
        listOfCompany.add(Company("Apple"))
        listOfCompany.add(Company("Samsung"))
        listOfCompany.add(Company("Motorola"))
        listOfCompany.add(Company("Microsoft"))
        listOfCompany.add(Company("Google"))

        recyclerAdapter = CompanyRecyclerAdapter(context, listOfCompany)

        recyclerView.layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false)

        recyclerView.adapter = recyclerAdapter

        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(context, recyclerView, object: RecyclerItemClickListener.OnItemClickListener{
                override fun onItemClick(view: View, position: Int) {

                    listener.setCurrentCompanyNo(position)

                    Utils.showMessageShort(context, listOfCompany[position].name)

                    // Go to Child not Found Screen
                    val productFragment = ProductFragment()
                    val fragmentManager = activity.supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.mainLayout, productFragment)
                    fragmentTransaction.addToBackStack(null)

                    // Commit the transaction
                    fragmentTransaction.commit()

                }

                override fun onLongItemClick(view: View, position: Int) {

                }
            })
        )


        return view


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as CompanyFragmentListener
    }


    override fun onClick(view: View?) {
    }



}
