package com.example.githubsearchapp.model

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Calendar

class RepoDetailsModel {

    @SerializedName("id")
    var id: Int? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("full_name")
    var full_name: String? = null

    @SerializedName("html_url")
    var html_url: String? = null

    @SerializedName("avatar_url")
    var avatar_url: String? = null
}
