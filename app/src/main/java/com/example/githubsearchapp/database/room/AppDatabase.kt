package com.example.githubsearchapp.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.githubsearchapp.database.converter.DataConverters
import com.example.githubsearchapp.database.dao.RepoDetailsDAO
import com.example.githubsearchapp.database.entity.RepoDetailsEntity
import com.example.githubsearchapp.utils.Constants

@Database(entities = [RepoDetailsEntity::class], version = 1, exportSchema = false)
@TypeConverters(DataConverters::class)
abstract class AppDatabase : RoomDatabase(){

    //Define all DAO class here
    abstract fun repoDetailsEntity(): RepoDetailsDAO

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
                AppDatabase::class.java, "${"Constants.DB_NAME"}.db")
                .build()
    }
}