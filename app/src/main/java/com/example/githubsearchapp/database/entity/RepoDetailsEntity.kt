package com.example.githubsearchapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.githubsearchapp.utils.Constants
import com.google.gson.annotations.SerializedName

@Entity(tableName = Constants.TABLE_NAME)
data class RepoDetailsEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "full_name")
    var full_name: String? = null,

    @ColumnInfo(name = "description")
    var description: String? = null,

    @ColumnInfo(name = "html_url")
    var html_url: String? = null,

    @ColumnInfo(name = "avatar_url")
    var avatar_url: String? = null,

    @ColumnInfo(name = "contributors_url")
    var contributors_url: String? = null
)
