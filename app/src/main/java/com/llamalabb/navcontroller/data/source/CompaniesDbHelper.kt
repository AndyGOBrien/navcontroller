package com.llamalabb.navcontroller.data.source

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.llamalabb.navcontroller.data.source.CompaniesPersistenceContract.CompaniesEntry.COLUMN_NAME_TICKER
import com.llamalabb.navcontroller.data.source.CompaniesPersistenceContract.CompaniesEntry.COLUMN_NAME_ENTRY_ID
import com.llamalabb.navcontroller.data.source.CompaniesPersistenceContract.CompaniesEntry.COLUMN_NAME_NAME
import com.llamalabb.navcontroller.data.source.CompaniesPersistenceContract.CompaniesEntry.TABLE_NAME

/**
 * Created by andy on 11/9/17.
 */

class CompaniesDbHelper(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
    }


    companion object {
        val DATABASE_NAME = "Companies.db"
        val DATABASE_VERSION = 1
        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE $TABLE_NAME (" +
                        "$COLUMN_NAME_ENTRY_ID TEXT PRIMARY KEY," +
                        "$COLUMN_NAME_NAME TEXT," +
                        "$COLUMN_NAME_TICKER TEXT)"
    }
}