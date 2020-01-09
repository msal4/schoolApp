package com.smart.resources.schools_app.features.homework

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.setSpinnerList
import com.smart.resources.schools_app.databinding.FragmentAddHomeworkBinding
import com.smart.resources.schools_app.features.containerActivities.SectionActivity
import com.smart.resources.schools_app.core.util.toast
import com.smart.resources.schools_app.features.profile.TeacherInfoModel
import java.util.logging.Logger

class AddHomeworkFragment : Fragment() {
    private lateinit var binding: FragmentAddHomeworkBinding
    private lateinit var viewModel: AddHomeworkViewModel

    companion object {
        private const val ADD_EXAM_FRAGMENT= "addExamFragment"

        fun newInstance(fm:FragmentManager){
                val fragment=
                    AddHomeworkFragment()
            fm.beginTransaction().apply {
                setCustomAnimations(R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    R.anim.slide_in_left,
                    R.anim.slide_out_right)
                replace(R.id.fragmentContainer, fragment)
                addToBackStack(ADD_EXAM_FRAGMENT)
                commit()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddHomeworkBinding
            .inflate(inflater, container, false)

        ViewModelProviders
            .of(this)
            .get(AddHomeworkViewModel::class.java)
            .apply {
                viewModel= this
                binding.postModel= postHomeworkModel
        }

        TeacherInfoModel
            .instance
            ?.classesInfo
            ?.let {
            setSpinnerList(binding.ClassAndSectionSpinner, it)
        }

        setHasOptionsMenu(true)
        (activity as SectionActivity).setCustomTitle(R.string.add_homework)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save_btn, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.saveMenuItem-> {
                viewModel.addHomework()
                true
            }
        }

        return super.onOptionsItemSelected(item)
    }

}