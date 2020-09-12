package com.smart.resources.schools_app.features.homework.getHomeworks

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
import com.smart.resources.schools_app.core.extentions.showSnackBar
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.features.homework.HomeworkModel
import com.smart.resources.schools_app.features.homework.HomeworkViewModel
import com.smart.resources.schools_app.features.homework.addHomework.AddHomeworkFragment
import com.smart.resources.schools_app.features.homeworkSolution.presentation.fragments.HomeworkSolutionBottomSheet
import com.smart.resources.schools_app.features.homeworkSolution.presentation.fragments.HomeworkAnswerFragment
import com.smart.resources.schools_app.features.users.UsersRepository
import com.smart.resources.schools_app.sharedUi.ImageViewerActivity
import com.smart.resources.schools_app.sharedUi.SectionActivity

class HomeworkFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private lateinit var viewModel: HomeworkViewModel
    private lateinit var adapter: HomeworkRecyclerAdapter


    companion object {
        fun newInstance(fm: FragmentManager) {
            val fragment =
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
        viewModel = ViewModelProviders.of(activity!!)
            .get(HomeworkViewModel::class.java)
        binding = FragmentRecyclerLoaderBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@HomeworkFragment
            listState = viewModel.listState
            viewModel.onError = ::onError

            viewModel.getHomework.observe(this@HomeworkFragment, Observer {
                if (it == null) return@Observer
                errorText.text = ""
                adapter.submitList(it)
            })

        }
        adapter = HomeworkRecyclerAdapter(viewModel.isStudent).apply {
            onImageClicked = ::onImageClicked
            onAnswerClicked = ::onAnswerClicked
        }
        binding.recyclerView.adapter = adapter

        (activity as SectionActivity).setCustomTitle(R.string.homework)
        setHasOptionsMenu(true)
        if (UsersRepository.instance.getCurrentUserAccount()?.userType == 1) {
            val touchHelper = ItemTouchHelper(SwipeAdapter(::onSwipe))
            touchHelper.attachToRecyclerView(binding.recyclerView)
        }
        return binding.root
    }

    private fun onSwipe(viewHolder: RecyclerView.ViewHolder) {
        viewModel.deleteHomework(viewHolder.adapterPosition)
    }

    private fun onImageClicked(imageView: ImageView, imageUrl: String) {
        activity?.let { ImageViewerActivity.newInstance(it, imageView, imageUrl) }
    }

    private fun onAnswerClicked(homeworkModel: HomeworkModel) {
        fragmentManager?.let {
            if (viewModel.isStudent) {
                HomeworkSolutionBottomSheet.newInstance(homeworkModel.idHomework.toString()).show(it, "")
            } else {
                HomeworkAnswerFragment.newInstance(it)
            }
        }
    }


    fun onError(errorMsg: String) {
        binding.layout.showSnackBar(errorMsg)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (UsersRepository.instance.getCurrentUserAccount()?.userType == 1) {

            inflater.inflate(R.menu.menu_add_btn, menu)
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addMenuItem -> fragmentManager?.let { AddHomeworkFragment.newInstance(it) }
        }
        return super.onOptionsItemSelected(item)
    }
}
