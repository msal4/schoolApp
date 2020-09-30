package com.smart.resources.schools_app.features.onlineExam.presentation.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.activity.SectionActivity
import com.smart.resources.schools_app.core.bindingAdapters.setSwipeToDelete
import com.smart.resources.schools_app.core.typeConverters.room.OnlineExamStatus
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.features.onlineExam.domain.model.OnlineExam
import com.smart.resources.schools_app.features.onlineExam.domain.viewModel.OnlineExamViewModel
import com.smart.resources.schools_app.features.onlineExam.presentation.adapter.OnlineExamAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnlineExamsFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private lateinit var adapter: OnlineExamAdapter
    private val viewModel: OnlineExamViewModel by viewModels()

    companion object {
        fun newInstance(fm: FragmentManager) {
            val fragment = OnlineExamsFragment()
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
            lifecycleOwner = this@OnlineExamsFragment
            listState = viewModel.listState


            setupRecycler()
            setupObservers()
        }

        setHasOptionsMenu(true)
        (activity as SectionActivity).setCustomTitle(R.string.online_exam)
        return binding.root
    }

    private fun setupObservers() {
        viewModel.onlineExams.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun FragmentRecyclerLoaderBinding.setupRecycler() {
        adapter = OnlineExamAdapter(true)
        adapter.onItemPressed = ::onOnlineExamPressed

        if(viewModel.isTeacher) {
            recyclerView.setSwipeToDelete(viewModel::removeExam)
        }
        recyclerView.adapter = adapter
    }

    private fun onOnlineExamPressed(onlineExam: OnlineExam) {
        // TODO: test this logic
        val readOnly= viewModel.isTeacher || (onlineExam.examStatus != OnlineExamStatus.ACTIVE)
        ExamPaperFragment.newInstance(parentFragmentManager, onlineExam, readOnly)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_btn, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addMenuItem -> if (isAdded) {
                if(viewModel.isTeacher) {
                    AddOnlineExamFragment.newInstance(parentFragmentManager)
                }else{

                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}