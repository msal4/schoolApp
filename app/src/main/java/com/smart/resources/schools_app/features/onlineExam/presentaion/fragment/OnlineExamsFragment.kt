package com.smart.resources.schools_app.features.onlineExam.presentaion.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.features.onlineExam.domain.model.OnlineExamDetails
import com.smart.resources.schools_app.features.onlineExam.domain.model.OnlineExamStatus
import com.smart.resources.schools_app.features.onlineExam.domain.viewModel.OnlineExamViewModel
import com.smart.resources.schools_app.features.onlineExam.presentaion.adapter.OnlineExamAdapter
import com.smart.resources.schools_app.features.users.UsersRepository
import com.smart.resources.schools_app.sharedUi.SectionActivity
import org.threeten.bp.Duration
import org.threeten.bp.LocalDateTime

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


            adapter = OnlineExamAdapter(true)

            adapter.onItemPressed= ::onOnlineExamPressed
            adapter.submitList(dummyOnlineExams)
            viewModel.listState.setLoading(false) // TODO: remove

            recyclerView.adapter = adapter
        }

        setHasOptionsMenu(true)
        (activity as SectionActivity).setCustomTitle(R.string.online_exam)
        return binding.root
    }

    private fun onOnlineExamPressed(onlineExamDetails: OnlineExamDetails){
        QuestionsFragment.newInstance(parentFragmentManager, onlineExamDetails)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (UsersRepository.instance.getCurrentUserAccount()?.userType == 1) {
            inflater.inflate(R.menu.menu_add_btn, menu)
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addMenuItem -> if (isAdded) {
                AddOnlineExamFragment.newInstance(parentFragmentManager)
            }
        }
        return super.onOptionsItemSelected(item)
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