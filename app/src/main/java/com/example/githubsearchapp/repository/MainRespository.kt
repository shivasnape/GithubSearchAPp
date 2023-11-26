package com.example.githubsearchapp.repository

import android.os.Handler
import androidx.compose.runtime.key
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubsearchapp.database.entity.RepoDetailsEntity
import com.example.githubsearchapp.database.room.AppDatabase
import com.example.githubsearchapp.model.ContributersResponse
import com.example.githubsearchapp.model.Items
import com.example.githubsearchapp.model.RepositoryResponse
import com.example.githubsearchapp.retrofit.api.APIService
import com.example.githubsearchapp.utils.Coroutines
import com.fmq.ticketmonitoringsystem.retrofit.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRespository(val apiService: APIService, val database : AppDatabase) : BaseRepository() {


    fun getRepositryList(auth: String, keyword: String): LiveData<RepositoryResponse> {

        val data: MutableLiveData<RepositoryResponse> = MutableLiveData()

        Coroutines.io {
            val result = callGetRepositoryAPI(auth, keyword)
            if (result != null) {
                data.postValue(result)
            } else {
                data.postValue(null)
            }
        }

        return data
    }

    suspend fun callGetRepositoryAPI(token: String, keyword : String): RepositoryResponse? {
        return getSafeApiCall(
            call = { apiService.getRepositories(token, keyword) },
            errorMessage = "Sync Error"
        )
    }

    fun insert(items: List<Items>) {

        Coroutines.io {

            items.forEach {

                val data = RepoDetailsEntity()
                data.name = it.repository.name
                data.full_name = it.repository.full_name
                data.html_url = it.repository.html_url
                data.avatar_url = it.repository.owner?.avatar_url?:"-"

                database.repoDetailsEntity().insert(data)
            }

        }

    }

    fun dropTable() {

        Coroutines.io {
            database.repoDetailsEntity().dropTable()
        }

    }

    fun getLocalSavedData() : LiveData<List<RepoDetailsEntity>> {

        val result : MutableLiveData<List<RepoDetailsEntity>> = MutableLiveData()

        Coroutines.io {
            val res = database.repoDetailsEntity().getAll()
            if(res!=null) {
                result.postValue(res)
            }
        }
        return result
    }

    fun getContributersList(url : String): LiveData<List<ContributersResponse>> {

        val data: MutableLiveData<List<ContributersResponse>> = MutableLiveData()

        Coroutines.io {
            val result = callContributersURL(url)
            if (result != null) {
                data.postValue(result)
            } else {
                data.postValue(null)
            }
        }

        return data
    }

    suspend fun callContributersURL(url : String): List<ContributersResponse>? {
        return getSafeApiCall(
            call = { apiService.getContributers(url) },
            errorMessage = "Sync Error"
        )
    }
}