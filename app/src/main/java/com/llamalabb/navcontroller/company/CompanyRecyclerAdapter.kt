package com.llamalabb.navcontroller.company

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop
import com.llamalabb.navcontroller.GlideApp
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
        var logoImg: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.txtView ->
                Utils.showMessageShort(context, txtViewStr)
        }
    }

    override fun getItemCount(): Int {return verticalList.size}

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val name = verticalList[position].name
        val url = verticalList[position].logoURL

        holder.txtView.text = name
        txtViewStr = holder.txtView.text.toString()
        holder.txtView.setOnClickListener(this)

        GlideApp.with(context)
                .load(url)
                .centerCrop()
                .fitCenter()
                .into(holder.logoImg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : MyViewHolder{

        var itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.vertical_base_item_view, parent, false)

        return MyViewHolder(itemView)
    }

}