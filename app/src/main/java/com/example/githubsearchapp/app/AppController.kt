package com.example.githubsearchapp.app

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.example.githubsearchapp.database.room.AppDatabase
import com.example.githubsearchapp.repository.MainRespository
import com.example.githubsearchapp.retrofit.api.APIService
import com.example.githubsearchapp.retrofit.base.RetrofitNetwork
import com.example.githubsearchapp.retrofit.interceptor.NetworkConnectionInterceptor
import com.example.githubsearchapp.viewmodel.MainViewModel
import com.example.githubsearchapp.viewmodel.common.ViewModelFactory
import com.example.githubsearchapp.viewmodel.common.bindViewModel

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import org.kodein.di.Kodein.Companion.lazy

class AppController : Application(), KodeinAware {

    companion object {
        lateinit var instance : AppController
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override val kodein = lazy {
        import(androidXModule(this@AppController))
        bind<Kodein>() with singleton{ kodein }
        bind() from singleton { AppDatabase(instance()) }
        bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(kodein.direct) }
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind<APIService>() with singleton { RetrofitNetwork.getApiInterface(instance()) }
        //====================================================================================================
        //View Model
        //====================================================================================================
        bindViewModel<MainViewModel>() with provider { MainViewModel(instance()) }
        //====================================================================================================
        //Repository
        //====================================================================================================
        bind() from singleton { MainRespository(instance(), instance()) }
    }
}