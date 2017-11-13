package com.llamalabb.navcontroller.data.source

import android.content.ContentValues
import android.content.Context
import com.llamalabb.navcontroller.data.CompaniesDataSource
import com.llamalabb.navcontroller.data.Company
import com.llamalabb.navcontroller.data.Product
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
class CompaniesLocalDataSource(context: Context) : CompaniesDataSource{

    private val dbHelper: CompaniesDbHelper = CompaniesDbHelper(context)


    override fun getCompanies(callback: CompaniesDataSource.LoadCompaniesCallback) {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
                COLUMN_COMPANY_ENTRY_ID,
                COLUMN_COMPANY_NAME,
                COLUMN_COMPANY_DOMAIN,
                COLUMN_COMPANY_TICKER
        )

        val cursor = db.query(
                COMPANY_TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        )

        val companies = ArrayList<Company>()
        with(cursor) {
            while (moveToNext()) {
                val itemId = getString(getColumnIndexOrThrow(COLUMN_COMPANY_ENTRY_ID))
                val name = getString(getColumnIndexOrThrow(COLUMN_COMPANY_NAME))
                val domain = getString(getColumnIndexOrThrow(COLUMN_COMPANY_DOMAIN))
                val ticker = getString(getColumnIndexOrThrow(COLUMN_COMPANY_TICKER))
                val company = Company(name, domain, initialStockTicker =  ticker, id = itemId)
                companies.add(company)
            }
            close()
        }
        db.close()

        if (companies.isNotEmpty()) {
            callback.onCompaniesLoaded(companies)
        } else {
            callback.onDataNotAvailable()
        }
    }

    override fun getProducts(companyId: String, callback: CompaniesDataSource.LoadProductsCallback) {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
                COLUMN_PRODUCT_ENTRY_ID,
                COLUMN_PRODUCT_NAME,
                COLUMN_PRODUCT_COMPANY_ID,
                COLUMN_PRODUCT_PAGE_URL,
                COLUMN_PRODUCT_LOGO_URL
        )

        val cursor = db.query(
                PRODUCT_TABLE_NAME,
                projection,
                "$COLUMN_PRODUCT_COMPANY_ID LIKE ?",
                arrayOf(companyId),
                null,
                null,
                null
        )

        val products = ArrayList<Product>()
        with(cursor){
            while(moveToNext()){
                val itemId = getString(getColumnIndexOrThrow(COLUMN_PRODUCT_ENTRY_ID))
                val name = getString(getColumnIndexOrThrow(COLUMN_PRODUCT_NAME))
                val companyId = getString(getColumnIndexOrThrow(COLUMN_PRODUCT_COMPANY_ID))
                val productPageUrl = getString(getColumnIndexOrThrow(COLUMN_PRODUCT_PAGE_URL))
                val productLogoUrl = getString(getColumnIndexOrThrow(COLUMN_PRODUCT_LOGO_URL))
                val product = Product(name, companyId, productLogoUrl, productPageUrl, itemId)
                products.add(product)
            }
            close()
        }
        db.close()

        if(products.isNotEmpty()){
            callback.onProductsLoaded(products)
        } else {
            callback.onDataNotAvailable()
        }
    }

     override fun getCompany(companyId: String, callback: CompaniesDataSource.GetCompanyCallback) {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
                COLUMN_COMPANY_ENTRY_ID,
                COLUMN_COMPANY_NAME,
                COLUMN_COMPANY_DOMAIN,
                COLUMN_COMPANY_TICKER
        )

        val cursor = db.query(
                COMPANY_TABLE_NAME,
                projection,
                "$COLUMN_COMPANY_ENTRY_ID LIKE ?",
                arrayOf(companyId),
                null,
                null,
                null
        )
        var company: Company? = null
        with(cursor) {
            if (moveToFirst()) {
                val itemId = getString(getColumnIndexOrThrow(COLUMN_COMPANY_ENTRY_ID))
                val name = getString(getColumnIndexOrThrow(COLUMN_COMPANY_NAME))
                val domain = getString(getColumnIndexOrThrow(COLUMN_COMPANY_DOMAIN))
                val ticker = getString(getColumnIndexOrThrow(COLUMN_COMPANY_TICKER))
                company = Company(name, domain, initialStockTicker = ticker, id = itemId)
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
            put(COLUMN_COMPANY_ENTRY_ID, company.id)
            put(COLUMN_COMPANY_NAME, company.name)
            put(COLUMN_COMPANY_DOMAIN, company.domain)
            put(COLUMN_COMPANY_TICKER, company.stockManager.stockTicker)
        }
        with(dbHelper.writableDatabase) {
            insert(COMPANY_TABLE_NAME, null, values)
            close()
        }
    }

    override fun saveProduct(product: Product) {
        val values = ContentValues().apply {
            put(COLUMN_PRODUCT_ENTRY_ID, product.id)
            put(COLUMN_PRODUCT_NAME, product.name)
            put(COLUMN_PRODUCT_COMPANY_ID, product.companyID)
            put(COLUMN_PRODUCT_LOGO_URL, product.logoURL)
            put(COLUMN_PRODUCT_PAGE_URL, product.productURL)
        }
        with(dbHelper.writableDatabase) {
            insert(PRODUCT_TABLE_NAME, null, values)
            close()
        }
    }

    override fun deleteProduct(productId: String) {
        val selection = "$COLUMN_PRODUCT_ENTRY_ID LIKE ?"
        val selectionArgs = arrayOf(productId)
        with(dbHelper.writableDatabase) {
            delete(PRODUCT_TABLE_NAME, selection, selectionArgs)
            close()
        }
    }

    override fun deleteAllProducts(companyId: String) {
        val db = dbHelper.writableDatabase
        val selection = "$COLUMN_PRODUCT_COMPANY_ID LIKE ?"
        val selectionArgs = arrayOf(companyId)

        db.delete(PRODUCT_TABLE_NAME, selection, selectionArgs)

    }

    override fun deleteAllCompanies() {
        with(dbHelper.writableDatabase) {
            delete(COMPANY_TABLE_NAME, null, null)
            delete(PRODUCT_TABLE_NAME, null, null)
            close()
        }
    }

    override fun deleteCompany(companyId: String) {
        val selection = "$COLUMN_COMPANY_ENTRY_ID LIKE ?"
        val selectionArgs = arrayOf(companyId)
        with(dbHelper.writableDatabase) {
            delete(COMPANY_TABLE_NAME, selection, selectionArgs)
            close()
        }
    }

    companion object {
        private var INSTANCE: CompaniesLocalDataSource? = null

        @JvmStatic fun getInstance(context: Context) =
                INSTANCE ?: CompaniesLocalDataSource(context).apply { INSTANCE = this }
    }
}