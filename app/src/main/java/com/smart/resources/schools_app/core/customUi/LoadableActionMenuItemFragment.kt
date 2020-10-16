package com.smart.resources.schools_app.core.customUi

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.haytham.coder.extensions.hide
import com.haytham.coder.extensions.show
import com.smart.resources.schools_app.core.activity.SectionActivity

abstract class LoadableActionMenuItemFragment : Fragment() {
    private val mProgressBar:ProgressBar? by lazy {
        val a= activity
        if(a is SectionActivity){
            a.getToolbarProgressBar()
        }else null
    }

    private var mMenuItem:MenuItem?= null


    abstract val menuResourceId:Int
    abstract val loadableItemId:Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(menuResourceId, menu)
        mMenuItem= menu.findItem(loadableItemId)
        super.onCreateOptionsMenu(menu, inflater)
    }

    fun setToolbarLoading(isLoading: Boolean) {
        if(isLoading){
            mProgressBar?.show()
            mMenuItem?.isVisible = false
        }else{
            mProgressBar?.hide()
            mMenuItem?.isVisible = true
        }
    }

    override fun onDestroyOptionsMenu() {
        setToolbarLoading(false)
        super.onDestroyOptionsMenu()
    }
}