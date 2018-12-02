package ru.nickmiller.tinkofffintech.data.entity

import com.google.gson.annotations.SerializedName


open class ApiResponse(
    @SerializedName("detail") var message: String? = null
)