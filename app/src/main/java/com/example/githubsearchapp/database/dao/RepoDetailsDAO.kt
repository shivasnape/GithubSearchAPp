package com.example.githubsearchapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.githubsearchapp.database.entity.RepoDetailsEntity
import com.example.githubsearchapp.utils.Constants

@Dao
interface RepoDetailsDAO {

    @Query("SELECT * FROM ${Constants.TABLE_NAME}")
    fun getAll(): List<RepoDetailsEntity>

    @Insert
    fun insert(data: RepoDetailsEntity)

    @Delete
    fun delete(sample: RepoDetailsEntity)

    @Query("DELETE FROM ${Constants.TABLE_NAME}")
    fun dropTable()

    @Query("DELETE FROM ${Constants.TABLE_NAME} WHERE id LIKE :id")
    fun deleteByID(id : Int)

}