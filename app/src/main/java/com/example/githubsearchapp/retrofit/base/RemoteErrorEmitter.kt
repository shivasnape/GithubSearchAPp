package com.example.githubsearchapp.retrofit.base

import com.example.githubsearchapp.retrofit.base.ErrorType

interface RemoteErrorEmitter {
    fun onError(errorType: ErrorType, msg: String)
}