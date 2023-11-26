package com.example.githubsearchapp.retrofit.base;

import com.example.githubsearchapp.BuildConfig
import com.example.githubsearchapp.retrofit.api.APIService
import com.example.githubsearchapp.retrofit.interceptor.NetworkConnectionInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitNetwork {

    companion object {

        private const val NETWORK_CALL_TIMEOUT = 60

        //You can add cache history below are the params
        // @application.cacheDir,
        // @10 * 1024 * 1024  (10MB)
        fun retrofit(networkConnectionInterceptor: NetworkConnectionInterceptor): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(OkHttpClient.Builder()
                    .addInterceptor(networkConnectionInterceptor)
                    .addInterceptor(HttpLoggingInterceptor()
                        .apply {
                            level = if (BuildConfig.DEBUG)
                                HttpLoggingInterceptor.Level.BODY
                            else
                                HttpLoggingInterceptor.Level.NONE
                        })
                    .readTimeout(600L, TimeUnit.SECONDS)
                    .writeTimeout(600L, TimeUnit.SECONDS)
                    .connectTimeout(1000L, TimeUnit.SECONDS)
                    .build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                /*.addCallAdapterFactory(RxJava2CallAdapterFactory.create())*/
                .build()
        }

        //Add Whatever service instances needed
        fun getApiInterface (networkConnectionInterceptor: NetworkConnectionInterceptor): APIService {
            return retrofit(networkConnectionInterceptor).create(APIService::class.java)
        }

    }
}