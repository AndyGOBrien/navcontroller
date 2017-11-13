package com.llamalabb.navcontroller.data.source

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.llamalabb.navcontroller.data.source.CompaniesPersistenceContract.CompaniesEntry.COLUMN_COMPANY_DOMAIN
import com.llamalabb.navcontroller.data.source.CompaniesPersistenceContract.CompaniesEntry.COLUMN_COMPANY_TICKER
import com.llamalabb.navcontroller.data.source.CompaniesPersistenceContract.CompaniesEntry.COLUMN_COMPANY_ENTRY_ID
import com.llamalabb.navcontroller.data.source.CompaniesPersistenceContract.CompaniesEntry.COLUMN_COMPANY_NAME
import com.llamalabb.navcontroller.data.source.CompaniesPersistenceContract.CompaniesEntry.COMPANY_TABLE_NAME
import com.llamalabb.navcontroller.data.source.CompaniesPersistenceContract.ProductsEntry.COLUMN_PRODUCT_COMPANY_ID
import com.llamalabb.navcontroller.data.source.CompaniesPersistenceContract.ProductsEntry.COLUMN_PRODUCT_ENTRY_ID
import com.llamalabb.navcontroller.data.source.CompaniesPersistenceContract.ProductsEntry.COLUMN_PRODUCT_LOGO_URL
import com.llamalabb.navcontroller.data.source.CompaniesPersistenceContract.ProductsEntry.COLUMN_PRODUCT_NAME
import com.llamalabb.navcontroller.data.source.CompaniesPersistenceContract.ProductsEntry.COLUMN_PRODUCT_PAGE_URL
import com.llamalabb.navcontroller.data.source.CompaniesPersistenceContract.ProductsEntry.PRODUCT_TABLE_NAME

/**
 * Created by andy on 11/9/17.
 */

class CompaniesDbHelper(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_COMPANY_ENTRIES)
        db.execSQL(SQL_CREATE_PRODUCT_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
    }


    companion object {
        val DATABASE_NAME = "Companies.db"
        val DATABASE_VERSION = 1
        private val SQL_CREATE_COMPANY_ENTRIES =
                "CREATE TABLE $COMPANY_TABLE_NAME (" +
                        "$COLUMN_COMPANY_ENTRY_ID TEXT PRIMARY KEY," +
                        "$COLUMN_COMPANY_NAME TEXT," +
                        "$COLUMN_COMPANY_DOMAIN TEXT," +
                        "$COLUMN_COMPANY_TICKER TEXT)"

        private val SQL_CREATE_PRODUCT_ENTRIES =
                "CREATE TABLE $PRODUCT_TABLE_NAME (" +
                        "$COLUMN_PRODUCT_ENTRY_ID TEXT PRIMARY KEY," +
                        "$COLUMN_PRODUCT_NAME TEXT," +
                        "$COLUMN_PRODUCT_COMPANY_ID TEXT," +
                        "$COLUMN_PRODUCT_PAGE_URL TEXT," +
                        "$COLUMN_PRODUCT_LOGO_URL TEXT)"
    }
}