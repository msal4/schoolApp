package com.smart.resources.schools_app.core.myTypes

interface PostListener{
    fun onUploadComplete()
    fun onUploadStarted()
    fun onError(errorMsg:String)
}
