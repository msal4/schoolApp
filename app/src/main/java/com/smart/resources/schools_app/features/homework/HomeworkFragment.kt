package com.smart.resources.schools_app.features.homework

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.adapters.SwipeAdapter
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.features.profile.AccountManager
import com.smart.resources.schools_app.sharedUi.ImageViewerActivity
import com.smart.resources.schools_app.sharedUi.SectionActivity

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
        adapter = HomeworkRecyclerAdapter(::onImageClick)
        binding.recyclerView.adapter = adapter
        (activity as SectionActivity).setCustomTitle(R.string.homework)
        setHasOptionsMenu(true)

        setupViewModel()

        if(AccountManager.instance?.getCurrentUser()?.userType==1) {

            val touchHelper =ItemTouchHelper( SwipeAdapter(::onSwipe))

            touchHelper.attachToRecyclerView(binding.recyclerView)
        }
        return binding.root
    }

    private fun onSwipe(viewHolder: RecyclerView.ViewHolder ){
        viewModel.deleteHomework(viewHolder.adapterPosition)
    }

    private fun onImageClick(imageView: ImageView, imageUrl: String){
        activity?.let { ImageViewerActivity.newInstance(it,imageView, imageUrl) }
    }


    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(activity!!)
            .get(HomeworkViewModel::class.java).apply {
                binding.listState= listState

                getHomework.observe(this@HomeworkFragment, Observer{
                    if(it == null) return@Observer

                    binding.errorText.text= ""
                    adapter.submitList(it)
                })

            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if(AccountManager.instance?.getCurrentUser()?.userType==1) {

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
}
