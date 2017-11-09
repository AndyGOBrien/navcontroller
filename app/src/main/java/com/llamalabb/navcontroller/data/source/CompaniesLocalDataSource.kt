package com.llamalabb.navcontroller.data.source

import android.content.ContentValues
import android.content.Context
import com.llamalabb.navcontroller.data.CompaniesDataSource
import com.llamalabb.navcontroller.data.Company
import com.llamalabb.navcontroller.data.source.CompaniesPersistenceContract.CompaniesEntry.COLUMN_NAME_TICKER
import com.llamalabb.navcontroller.data.source.CompaniesPersistenceContract.CompaniesEntry.COLUMN_NAME_ENTRY_ID
import com.llamalabb.navcontroller.data.source.CompaniesPersistenceContract.CompaniesEntry.COLUMN_NAME_NAME
import com.llamalabb.navcontroller.data.source.CompaniesPersistenceContract.CompaniesEntry.TABLE_NAME

/**
 * Created by andy on 11/9/17.
 */
class CompaniesLocalDataSource(context: Context) : CompaniesDataSource{

    private val dbHelper: CompaniesDbHelper = CompaniesDbHelper(context)

    /**
     * Note: [CompaniesDataSource.LoadCompaniesCallback.onDataNotAvailable] is fired if the database doesn't exist
     * or the table is empty.
     */
    override fun getCompanies(callback: CompaniesDataSource.LoadCompaniesCallback) {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(COLUMN_NAME_ENTRY_ID, COLUMN_NAME_NAME,
                COLUMN_NAME_TICKER)

        val cursor = db.query(
                TABLE_NAME, projection, null, null, null, null, null)

        val companies = ArrayList<Company>()
        with(cursor) {
            while (moveToNext()) {
                val itemId = getString(getColumnIndexOrThrow(COLUMN_NAME_ENTRY_ID))
                val title = getString(getColumnIndexOrThrow(COLUMN_NAME_NAME))
                val ticker = getString(getColumnIndexOrThrow(COLUMN_NAME_TICKER))
                val company = Company(title, ticker, itemId)
                companies.add(company)
            }
            close()
        }
        db.close()

        if (companies.isNotEmpty()) {
            callback.onCompaniesLoaded(companies)
        } else {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable()
        }
    }


     override fun getCompany(companyId: String, callback: CompaniesDataSource.GetCompanyCallback) {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(COLUMN_NAME_ENTRY_ID, COLUMN_NAME_NAME,
                COLUMN_NAME_TICKER)

        val cursor = db.query(
                TABLE_NAME, projection, "$COLUMN_NAME_ENTRY_ID LIKE ?", arrayOf(companyId), null,
                null, null)
        var company: Company? = null
        with(cursor) {
            if (moveToFirst()) {
                val itemId = getString(getColumnIndexOrThrow(COLUMN_NAME_ENTRY_ID))
                val title = getString(getColumnIndexOrThrow(COLUMN_NAME_NAME))
                val description = getString(getColumnIndexOrThrow(COLUMN_NAME_TICKER))
                company = Company(title, description, itemId)
            }
            close()
        }
        db.close()

        company?.also { callback.onCompanyLoaded(it) }
                // This will be called if the table is new or just empty.
                ?: callback.onDataNotAvailable()
    }

    override fun saveCompany(company: Company) {
        val values = ContentValues().apply {
            put(COLUMN_NAME_ENTRY_ID, company.id)
            put(COLUMN_NAME_NAME, company.name)
            put(COLUMN_NAME_TICKER, company.stockManager.stockTicker)
        }
        with(dbHelper.writableDatabase) {
            insert(TABLE_NAME, null, values)
            close()
        }
    }

    override fun deleteAllCompanies() {
        with(dbHelper.writableDatabase) {
            delete(TABLE_NAME, null, null)
            close()
        }
    }

    override fun deleteCompany(companyId: String) {
        val selection = "$COLUMN_NAME_ENTRY_ID LIKE ?"
        val selectionArgs = arrayOf(companyId)
        with(dbHelper.writableDatabase) {
            delete(TABLE_NAME, selection, selectionArgs)
            close()
        }
    }

    companion object {
        private var INSTANCE: CompaniesLocalDataSource? = null

        @JvmStatic fun getInstance(context: Context) =
                INSTANCE ?: CompaniesLocalDataSource(context).apply { INSTANCE = this }
    }
}