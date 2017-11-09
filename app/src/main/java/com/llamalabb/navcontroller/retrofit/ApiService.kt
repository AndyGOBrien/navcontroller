package com.llamalabb.navcontroller.retrofit

import com.llamalabb.navcontroller.PotentialCompany
import com.llamalabb.navcontroller.data.stock.StockDataMap
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Created by andy on 11/6/17.
 */
interface ApiService {
    @GET("/query")
    fun getStockInfo(
            @Query(value="symbol") ticker: String?,
            @Query(value="function") function: String = "TIME_SERIES_INTRADAY",
            @Query(value="interval") interval: String = "1min",
            @Query(value="apikey") apikey: String = "JRU0T191Z90UXGJX")
            : Call<StockDataMap>

    @GET("/v1/companies/suggest")
    fun getCompanyInfo(@Query(value = "query") partialName: String) : Call<ArrayList<PotentialCompany>>

    @GET("/v1/domains/find")

    fun getCompanyDomain(@Header("authorization") auth: String =
                         "Bearer sk_92750edd0188abc30106de1551643f30",
                         @Query(value = "name") name: String)
            : Call<PotentialCompany>
}