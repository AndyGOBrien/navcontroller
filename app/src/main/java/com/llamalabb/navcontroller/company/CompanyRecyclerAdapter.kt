package com.llamalabb.navcontroller.company

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.llamalabb.navcontroller.R
import com.llamalabb.navcontroller.data.Company
import com.llamalabb.navcontroller.utils.Utils

/**
 * Created by andy on 10/24/17.
 */
class CompanyRecyclerAdapter(private val context: Context,
                             private var verticalList: List<Company>) :
        RecyclerView.Adapter<CompanyRecyclerAdapter.MyViewHolder>(),
        View.OnClickListener{

    lateinit var txtViewStr: String

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtView: TextView = view.findViewById(R.id.txtView)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.txtView ->
                Utils.showMessageShort(context, txtViewStr)
        }
    }

    override fun getItemCount(): Int {return verticalList.size}

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txtView.text = verticalList[position].name
        txtViewStr = holder.txtView.text.toString()
        holder.txtView.setOnClickListener(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : MyViewHolder{

        var itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.vertical_base_item_view, parent, false)

        return MyViewHolder(itemView)
    }

}