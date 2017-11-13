package com.llamalabb.navcontroller.companies

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by andy on 11/8/17.
 */
data class PotentialCompany(
        @SerializedName("domain")
        @Expose
        val domain: String,

        @SerializedName("logo")
        @Expose
        val logo: String,

        @SerializedName("name")
        @Expose
        val name: String
)