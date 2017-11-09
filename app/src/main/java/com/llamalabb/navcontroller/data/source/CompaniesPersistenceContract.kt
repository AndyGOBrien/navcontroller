package com.llamalabb.navcontroller.data.source

import android.provider.BaseColumns

/**
 * Created by andy on 11/9/17.
 */
object CompaniesPersistenceContract {

    object CompaniesEntry : BaseColumns{

        val TABLE_NAME = "companies"
        val COLUMN_NAME_ENTRY_ID = "entryid"
        val COLUMN_NAME_NAME = "name"
        val COLUMN_NAME_TICKER = "ticker"

    }
}