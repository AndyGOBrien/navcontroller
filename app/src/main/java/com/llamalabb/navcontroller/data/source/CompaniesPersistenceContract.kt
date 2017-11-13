package com.llamalabb.navcontroller.data.source

import android.provider.BaseColumns

/**
 * Created by andy on 11/9/17.
 */
object CompaniesPersistenceContract {

    object CompaniesEntry : BaseColumns{

        val COMPANY_TABLE_NAME = "companies"
        val COLUMN_COMPANY_ENTRY_ID = "companyentryid"
        val COLUMN_COMPANY_NAME = "name"
        val COLUMN_COMPANY_TICKER = "ticker"
        val COLUMN_COMPANY_DOMAIN = "domain"

    }

    object ProductsEntry : BaseColumns{

        val PRODUCT_TABLE_NAME = "products"
        val COLUMN_PRODUCT_ENTRY_ID = "productentryid"
        val COLUMN_PRODUCT_NAME = "name"
        val COLUMN_PRODUCT_COMPANY_ID = "companyid"
        val COLUMN_PRODUCT_PAGE_URL = "pageurl"
        val COLUMN_PRODUCT_LOGO_URL = "logourl"

    }
}