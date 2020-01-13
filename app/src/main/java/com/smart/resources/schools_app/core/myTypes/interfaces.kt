package com.smart.resources.schools_app.core.myTypes

interface PostListener{
    fun onUploadCompleted()
    fun onUploadStarted()
    fun onError(errorMsg:String)
}

interface UploadListener{
    fun onUploadCompleted()
    fun onError(errorMsg:String)
}