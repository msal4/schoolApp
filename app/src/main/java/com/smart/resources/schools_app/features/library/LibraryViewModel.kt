package com.smart.resources.schools_app.features.library

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.myTypes.ConnectionError
import com.smart.resources.schools_app.core.myTypes.ListState
import com.smart.resources.schools_app.core.myTypes.ResponseError
import com.smart.resources.schools_app.core.myTypes.Success
import com.smart.resources.schools_app.core.network.RetrofitHelper
import kotlinx.coroutines.launch


class LibraryViewModel(application: Application) : AndroidViewModel(application){
    private val c= application.applicationContext
    val listState = ListState()

    private val books: MutableLiveData<List<LibraryModel>>
            by lazy { MutableLiveData<List<LibraryModel>>() }

    fun getBooks():
            LiveData<List<LibraryModel>> {
        fetchHomework()

        return books
    }


    private fun fetchHomework(){
        viewModelScope.launch {
            val result = RetrofitHelper.libraryClient.fetchLib()

            listState.apply {
                when (result) {
                    is Success -> {
                        if (result.data.isNullOrEmpty())
                            setBodyError(c.getString(R.string.no_library))
                        else {
                            setLoading(false)
                            books.value = result.data
                        }

                    }
                    is ResponseError -> setBodyError(result.combinedMsg)
                    is ConnectionError -> setBodyError(c.getString(R.string.connection_error))
                }
            }
        }
    }
}