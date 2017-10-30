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
import com.llamalabb.navcontroller.RecyclerItemClickListener
import com.llamalabb.navcontroller.data.Company
import java.util.*

/**
 * Created by andy on 10/24/17.
 */
class CompanyFragment : Fragment(),
        CompanyContract.View{

    lateinit var listener: CompanyFragmentListener

    interface CompanyFragmentListener {
        fun loadProducts()
    }

    override var presenter: CompanyContract.Presenter = CompanyPresenter(this)

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: CompanyRecyclerAdapter
    private lateinit var addButton: ImageButton
    private lateinit var backButton: ImageButton

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter.onStart()

        var view: View = inflater!!.inflate(
                R.layout.vertical_base_item_list, container,false)

        recyclerView = view.findViewById(R.id.vertical_recycler_view)

        presenter.showCompanyList()

        return view

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as CompanyFragmentListener
    }

    override fun setAdapter(list: List<Company>) {
        recyclerAdapter = CompanyRecyclerAdapter(context, list)
        recyclerView.adapter = recyclerAdapter
    }

    override fun setRecyclerViewLayoutManager() {
        recyclerView.layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false)
    }

    override fun setRecyclerOnItemClickListener() {
        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(context, recyclerView, object : RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                presenter.setCompanyNum(position)
                listener.loadProducts()
            }

            override fun onLongItemClick(view: View, position: Int) {
            }
        }))
    }
}
