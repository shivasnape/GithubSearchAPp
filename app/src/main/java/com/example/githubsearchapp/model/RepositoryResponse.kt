package com.example.githubsearchapp.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import java.io.Serial

data class RepositoryResponse(
    @SerializedName("total_count")
    val total_count : Int,
    @SerializedName("items")
    val items : List<Items>
)

data class Items(

    @SerializedName("repository")
    val repository : Repository,

)

data class Repository(
    @SerializedName("id")
    val id : Int,
    @SerializedName("name")
    val name : String,
    @SerializedName("full_name")
    val full_name : String,
    @SerializedName("private")
    val private : Boolean,
    @SerializedName("html_url")
    val html_url : String,
    @SerializedName("description")
    val description : String,
    @SerializedName("owner")
    val owner : Owner?,
    @SerializedName("contributors_url")
    var contributors_url: String? = null

)

data class Owner(
    @SerializedName("avatar_url")
    val avatar_url : String?,
)
