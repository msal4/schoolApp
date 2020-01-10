package com.smart.resources.schools_app.features.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.core.myTypes.toMyResult
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


    private fun fetchHomework(){
        viewModelScope.launch {
            val result = GlobalScope.async { BackendHelper.libraryDao.fetchLib() }.toMyResult()
            books.value = result
        }
    }
}