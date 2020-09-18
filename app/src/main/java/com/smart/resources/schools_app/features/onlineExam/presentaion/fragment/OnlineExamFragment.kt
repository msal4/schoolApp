package com.smart.resources.schools_app.features.onlineExam.presentaion.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.features.onlineExam.domain.model.OnlineExamItem
import com.smart.resources.schools_app.features.onlineExam.domain.model.OnlineExamStatus
import com.smart.resources.schools_app.features.onlineExam.domain.viewModel.OnlineExamViewModel
import com.smart.resources.schools_app.features.onlineExam.presentaion.adapter.OnlineExamRecyclerAdapter
import com.smart.resources.schools_app.sharedUi.SectionActivity
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime

class OnlineExamFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private lateinit var viewModel: OnlineExamViewModel
    private lateinit var adapter: OnlineExamRecyclerAdapter


    companion object {
        fun newInstance(fm: FragmentManager) {
            val fragment = OnlineExamFragment()
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
        viewModel = ViewModelProviders.of(activity!!).get(OnlineExamViewModel::class.java)

        binding = FragmentRecyclerLoaderBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@OnlineExamFragment
            listState = viewModel.listState

            adapter = OnlineExamRecyclerAdapter()

            adapter.submitList(dummyOnlineExams)
            viewModel.listState.setLoading(false) // TODO: remove

            recyclerView.adapter = adapter
        }

        (activity as SectionActivity).setCustomTitle(R.string.online_exam)
        return binding.root
    }


}

val dummyOnlineExams=  listOf(
    OnlineExamItem(
        "0",
        "رياضيات",
        LocalDateTime.now(),
        Duration.ofMinutes(150),
        5,
        OnlineExamStatus.IN_PROGRESS,
    ),
    OnlineExamItem(
        "1",
        "اللغة العربية",
        LocalDateTime.now(),
        Duration.ofMinutes(500),
        15,
        OnlineExamStatus.READY,
    ),
    OnlineExamItem(
        "2",
        "انكليزي",
        LocalDateTime.now(),
        Duration.ofMinutes(300),
        30,
        OnlineExamStatus.LOCKED,
    ),
    OnlineExamItem(
        "3",
        "فيزياء",
        LocalDateTime.now(),
        Duration.ofMinutes(50),
        60,
        OnlineExamStatus.COMPLETED,
    ),
)