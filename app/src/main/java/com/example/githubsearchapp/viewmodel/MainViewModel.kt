package com.example.githubsearchapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubsearchapp.database.entity.RepoDetailsEntity
import com.example.githubsearchapp.model.ContributersResponse
import com.example.githubsearchapp.model.Items
import com.example.githubsearchapp.model.RepositoryResponse
import com.example.githubsearchapp.repository.MainRespository

class MainViewModel(val repository: MainRespository) : ViewModel() {


   fun getRepositories(auth : String, keyword : String) : LiveData<RepositoryResponse> {
       return repository.getRepositryList(auth = auth, keyword = keyword)
   }

    fun insert(items: List<Items>) {
        repository.insert(items)
    }

    fun dropTable() {
        repository.dropTable()
    }

    fun getDataFromLocalDB()  : LiveData<List<RepoDetailsEntity>>{
        return repository.getLocalSavedData()
    }

    fun getContributers(url : String) : LiveData<List<ContributersResponse>> {
        return repository.getContributersList(url)
    }
}

