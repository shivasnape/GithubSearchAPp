package com.example.githubsearchapp.database.converter

import androidx.room.TypeConverter
import com.example.githubsearchapp.model.RepoDetailsModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class DataConverters {
    @TypeConverter
    fun fromStringToDailyList(moduleString: String?): RepoDetailsModel {
        val myType: Type = object : TypeToken<RepoDetailsModel?>() {}.type
        return Gson().fromJson(moduleString, myType)
    }

    @TypeConverter
    fun fromDailyListToString(moduleModel: RepoDetailsModel?): String? {
        val gson = Gson()
        val type: Type = object : TypeToken<RepoDetailsModel?>() {}.type
        return gson.toJson(moduleModel, type)
    }
}