package com.llamalabb.navcontroller.utils

import android.app.Application
import android.content.Context
import android.widget.Toast

/**
 * Created by andy on 10/18/17.
 */
object Utils : Application(){
    fun showMessageShort(context: Context = applicationContext, msg: String){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }
    fun showMessageLong(context: Context = applicationContext, msg: String){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show()
    }
}