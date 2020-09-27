package com.smart.resources.schools_app.features.homework.getHomeworks

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.callbacks.SwipeAdapter
import com.smart.resources.schools_app.core.extentions.showSnackBar
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.features.homework.HomeworkModel
import com.smart.resources.schools_app.features.homework.HomeworkViewModel
import com.smart.resources.schools_app.features.homework.addHomework.AddHomeworkFragment
import com.smart.resources.schools_app.features.homeworkSolution.data.model.HomeworkSolutionModel
import com.smart.resources.schools_app.features.homeworkSolution.presentation.fragments.AddHomeworkSolutionBottomSheet
import com.smart.resources.schools_app.features.homeworkSolution.presentation.fragments.HomeworkSolutionFragment
import com.smart.resources.schools_app.features.homeworkSolution.presentation.fragments.ShowHomeworkSolutionBottomSheet
import com.smart.resources.schools_app.features.users.UsersRepository
import com.smart.resources.schools_app.features.imageViewer.ImageViewerActivity
import com.smart.resources.schools_app.core.activity.SectionActivity

class HomeworkFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private lateinit var adapter: HomeworkRecyclerAdapter
    private val viewModel: HomeworkViewModel by activityViewModels()


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
        binding = FragmentRecyclerLoaderBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@HomeworkFragment
            listState = viewModel.listState
            viewModel.onError = ::onError

            viewModel.homework.observe(viewLifecycleOwner, Observer {
                if (it == null) return@Observer
                errorText.text = ""
                adapter.submitList(viewModel.homework.value)
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
                if(homeworkModel.solution == null) {
                    AddHomeworkSolutionBottomSheet.newInstance(homeworkModel.idHomework).apply {
                        show(it, "")
                        onSolutionAdded = ::onHomeworkSolutionAdded
                    }
                }else{
                    ShowHomeworkSolutionBottomSheet.newInstance(homeworkModel.solution).show(it, "")
                }
            } else {
                HomeworkSolutionFragment.newInstance(it, homeworkModel.idHomework)
            }
        }
    }
    private fun onHomeworkSolutionAdded(homeworkSolution: HomeworkSolutionModel) {
        binding.layout.showSnackBar(getString(R.string.solution_added_successfully), false)
        viewModel.addSolution(homeworkSolution)
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
