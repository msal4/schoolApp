package com.smart.resources.schools_app.features.homework.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.helpers.SharedPrefHelper
import com.smart.resources.schools_app.core.myTypes.*
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.sharedUi.SectionActivity
import com.smart.resources.schools_app.core.utils.*
import com.smart.resources.schools_app.features.homework.viewModel.HomeworkViewModel

class HomeworkFragment : Fragment(){
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private lateinit var viewModel: HomeworkViewModel
    private lateinit var adapter: HomeworkRecyclerAdapter


    companion object {
        fun newInstance(fm:FragmentManager){
            val fragment=
                HomeworkFragment()

            fm.beginTransaction().apply {
                add(R.id.fragmentContainer, fragment)
                commit()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecyclerLoaderBinding.inflate(inflater, container, false).apply {
            lifecycleOwner= this@HomeworkFragment
        }
        adapter= HomeworkRecyclerAdapter()
        binding.recyclerView.adapter= adapter
        (activity as SectionActivity).setCustomTitle(R.string.homework)
        setHasOptionsMenu(true)

        setupViewModel()
        return binding.root
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)
            .get(HomeworkViewModel::class.java).apply {
                onError= this@HomeworkFragment::onError
                fetchHomework() // TODO: improve performance by not fetching new data every time

                getHomework.observe(this@HomeworkFragment, Observer{
                    if(it == null) return@Observer

                    adapter.updateData(it)
                    binding.errorText.text= ""
                    binding.progressIndicator.hide()
                })

            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if(SharedPrefHelper.instance?.userType == UserType.TEACHER) {

            inflater.inflate(R.menu.menu_add_btn, menu)
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.addMenuItem-> fragmentManager?.let { AddHomeworkFragment.newInstance(it) }
        }
        return super.onOptionsItemSelected(item)
    }


     private fun onError(errorMsg: String) {
         binding.progressIndicator.hide()
        binding.errorText.text = errorMsg
    }
}
