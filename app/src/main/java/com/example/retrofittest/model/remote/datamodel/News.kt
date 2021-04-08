package com.example.retrofittest.model.remote.datamodel

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class News(
        var author: String?,
        var title: String,
        var description: String,
        @SerializedName("urlToImage")
        var image: String?,
        @SerializedName("publishedAt")
        var published: String
): Serializable