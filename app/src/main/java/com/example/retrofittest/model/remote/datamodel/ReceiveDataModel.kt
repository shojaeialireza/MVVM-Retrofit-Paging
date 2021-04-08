package com.example.retrofittest.model.remote.datamodel

import com.google.gson.annotations.SerializedName

data class ReceiveDataModel (
    @SerializedName("articles")
    val news:ArrayList<News>
)