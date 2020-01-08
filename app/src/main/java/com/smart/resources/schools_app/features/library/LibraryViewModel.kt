package com.smart.resources.schools_app.features.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.resources.schools_app.core.util.*
import kotlinx.coroutines.*

typealias LibraryResult= MyResult<List<LibraryModel>>

class LibraryViewModel : ViewModel() {
    private val books: MutableLiveData<LibraryResult>
            by lazy { MutableLiveData<LibraryResult>() }

    fun getBooks():
            LiveData<LibraryResult> {
        fetchHomework()

        return books
    }


    private val libraryDao: LibraryDao = BackendHelper
        .retrofitWithAuth
        .create(LibraryDao::class.java)


    private fun fetchHomework(){
        viewModelScope.launch {
            val result = GlobalScope.async { libraryDao.fetchLib() }.toMyResult()
            books.value = result
        }
    }
}