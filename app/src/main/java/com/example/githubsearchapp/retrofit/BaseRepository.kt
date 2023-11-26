package com.fmq.ticketmonitoringsystem.retrofit

import android.util.Log
import com.example.githubsearchapp.retrofit.api.Result
import com.google.gson.Gson
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException


open class BaseRepository {

    suspend fun < T : Any> getSafeApiCall(call: suspend () -> Response<T>, errorMessage: String): T? {

        val result: Result<T> = getSafeApiResult(call, errorMessage)
        var data: T? = null
        when (result) {
            is Result.Success -> {
                data = result.data

            }
            is Result.Error -> {
                data = null
                Log.d("1.DataRepository", "$errorMessage & Exception - ${result.exception}")
            }
        }

        return data

    }

    private suspend fun <T : Any> getSafeApiResult(
        call: suspend () -> Response<T>,
        errorMessage: String
    ): Result<T> {
        return try {
            val response = call.invoke()
            if (response.isSuccessful) {
                if (!response.body()!!.equals("")) {
                    Result.Success(response.body()!!)
                } else {
                    Result.Error(IOException("Response not received. Please try again"))
                }
            } else {
                Log.e("Retrofit error - ", Gson().toJson(response.code()))
                Result.Error(IOException("An issue occurred while processing your request"))
            }
        } catch (ex: SocketTimeoutException) {
            ex.printStackTrace()
            return Result.Error(IOException("Timeout. Please try again later"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return Result.Error(IOException("API Error. Please try again later"))
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.Error(IOException("An issue occurred while processing your request, Custom ERROR - $errorMessage"))
        }
    }
}