package com.example.githubsearchapp.retrofit.api


import com.example.githubsearchapp.model.ContributersResponse
import com.example.githubsearchapp.model.RepositoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url


interface APIService {

    @GET("/search/code")
    suspend fun getRepositories(@Header("Authorization") bearerToken : String, @Query("q") ticketId : String) : Response<RepositoryResponse>

    @GET
    suspend fun getContributers(@Url url: String): Response<List<ContributersResponse>>
}