package com.smart.resources.schools_app.features.onlineExam.presentaion.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.FragmentAddOnlineExamBinding
import com.smart.resources.schools_app.features.onlineExam.domain.viewModel.AddOnlineExamViewModel
import com.smart.resources.schools_app.features.onlineExam.presentaion.adapter.QuestionsPagerAdapter
import com.smart.resources.schools_app.sharedUi.SectionActivity

class AddOnlineExamFragment : Fragment() {
    private lateinit var binding: FragmentAddOnlineExamBinding
    private val viewModel: AddOnlineExamViewModel by viewModels()
    private lateinit var pagerAdapter: QuestionsPagerAdapter

    companion object {
        private const val ADD_ONLINE_EXAM_FRAGMENT = "addOnlineExamFragment"

        fun newInstance(fm: FragmentManager) {
            val fragment = AddOnlineExamFragment()
            fm.beginTransaction().apply {
                setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    R.anim.slide_in_left,
                    R.anim.slide_out_right,
                )
                replace(R.id.fragmentContainer, fragment)
                addToBackStack(ADD_ONLINE_EXAM_FRAGMENT)
                commit()
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddOnlineExamBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@AddOnlineExamFragment
            model= viewModel
        }
        (activity as SectionActivity).setCustomTitle(R.string.add_exam)
        return binding.root
    }



}

