package com.llamalabb.navcontroller.product

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.llamalabb.navcontroller.R
import com.llamalabb.navcontroller.data.Product
import com.llamalabb.navcontroller.utils.Utils

class ProductRecyclerAdapter(private val context: Context, private val verticalList: List<Product>)
    : RecyclerView.Adapter<ProductRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textProductName: TextView = view.findViewById(R.id.textProductName)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ProductRecyclerAdapter.MyViewHolder {

        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.vertical_product_view, parent, false)

        return ProductRecyclerAdapter.MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textProductName.text = verticalList[position].name
        holder.textProductName.
                setOnClickListener{
                    Utils.showMessageShort(context, holder.textProductName.text.toString()) }
    }

    override fun getItemCount(): Int {
        return verticalList.size
    }
}
