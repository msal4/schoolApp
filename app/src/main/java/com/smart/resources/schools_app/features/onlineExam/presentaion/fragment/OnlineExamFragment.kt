package com.smart.resources.schools_app.features.onlineExam.presentaion.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.features.onlineExam.domain.model.OnlineExamDetails
import com.smart.resources.schools_app.features.onlineExam.domain.model.OnlineExamStatus
import com.smart.resources.schools_app.features.onlineExam.domain.viewModel.OnlineExamViewModel
import com.smart.resources.schools_app.features.onlineExam.presentaion.adapter.OnlineExamAdapter
import com.smart.resources.schools_app.sharedUi.SectionActivity
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime

class OnlineExamFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private lateinit var adapter: OnlineExamAdapter
    private val viewModel: OnlineExamViewModel by viewModels()


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

        binding = FragmentRecyclerLoaderBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@OnlineExamFragment
            listState = viewModel.listState


            adapter = OnlineExamAdapter(true)

            adapter.onItemPressed= ::onOnlineExamPressed
            adapter.submitList(dummyOnlineExams)
            viewModel.listState.setLoading(false) // TODO: remove

            recyclerView.adapter = adapter
        }

        (activity as SectionActivity).setCustomTitle(R.string.online_exam)
        return binding.root
    }

    private fun onOnlineExamPressed(onlineExamDetails: OnlineExamDetails){
        QuestionsFragment.newInstance(parentFragmentManager, onlineExamDetails)
    }
}

val dummyOnlineExams=  listOf(
    OnlineExamDetails(
        "0",
        "رياضيات",
        LocalDateTime.now(),
        Duration.ofMinutes(150),
        LocalDateTime.now().plusMinutes(30),
        5,
        OnlineExamStatus.IN_PROGRESS,
    ),
    OnlineExamDetails(
        "1",
        "اللغة العربية",
        LocalDateTime.now(),
        Duration.ofMinutes(500),
        LocalDateTime.now().plusMinutes(30),
        15,
        OnlineExamStatus.READY,
    ),
    OnlineExamDetails(
        "2",
        "انكليزي",
        LocalDateTime.now(),
        Duration.ofMinutes(300),
        LocalDateTime.now().plusMinutes(30),
        30,
        OnlineExamStatus.LOCKED,
    ),
    OnlineExamDetails(
        "3",
        "فيزياء",
        LocalDateTime.now(),
        Duration.ofMinutes(50),
        LocalDateTime.now().plusMinutes(30),
        60,
        OnlineExamStatus.COMPLETED,
    ),
)