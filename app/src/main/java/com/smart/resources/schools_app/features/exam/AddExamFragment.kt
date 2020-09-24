package com.smart.resources.schools_app.features.exam

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.bindingAdapters.setSpinnerList
import com.smart.resources.schools_app.core.bindingAdapters.textView.setTextFromDate
import com.smart.resources.schools_app.core.myTypes.*
import com.smart.resources.schools_app.core.extentions.hide
import com.smart.resources.schools_app.core.extentions.show
import com.smart.resources.schools_app.core.extentions.showSnackBar
import com.smart.resources.schools_app.databinding.FragmentAddExamBinding
import com.smart.resources.schools_app.features.users.UsersRepository
import com.smart.resources.schools_app.features.profile.ClassInfoModel
import com.smart.resources.schools_app.features.profile.TeacherModel
import com.smart.resources.schools_app.sharedUi.DatePickerFragment
import com.smart.resources.schools_app.sharedUi.SectionActivity
import com.tiper.MaterialSpinner

class AddExamFragment : Fragment(), PostListener {
    private lateinit var binding: FragmentAddExamBinding
    private lateinit var progressBar: ProgressBar
    private lateinit var saveItem: MenuItem
    private val viewModel: ExamViewModel by activityViewModels()

    private val onClassSelected = object :
        MaterialSpinner.OnItemSelectedListener {
        override fun onItemSelected(
            parent: MaterialSpinner,
            view: View?,
            position: Int,
            id: Long
        ) {
            viewModel.postExamModel.classId =
                (parent.selectedItem as ClassInfoModel).classId.toString()
        }

        override fun onNothingSelected(parent: MaterialSpinner) {
        }

    }

    private val onExamTypeSelected = object :
        MaterialSpinner.OnItemSelectedListener {
        override fun onItemSelected(
            parent: MaterialSpinner,
            view: View?,
            position: Int,
            id: Long
        ) {
            viewModel.postExamModel.type = parent.selectedItem.toString()
        }

        override fun onNothingSelected(parent: MaterialSpinner) {
        }

    }

    companion object {
        private const val ADD_EXAM_FRAGMENT = "addExamFragment"

        fun newInstance(fm: FragmentManager) {
            val fragment =
                AddExamFragment()
            fm.beginTransaction().apply {
                setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    R.anim.slide_in_left,
                    R.anim.slide_out_right
                )
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
        setupBinding(inflater, container)
        setupViewModel()

        setHasOptionsMenu(true)
        (activity as SectionActivity).apply {
            setCustomTitle(R.string.add_exam)
            progressBar = getToolbarProgressBar()
            onBackPressedDispatcher
                .addCallback(viewLifecycleOwner, object :
                    OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        this@AddExamFragment.onBackPressed()
                    }
                })
        }

        return binding.root
    }

    private fun onBackPressed() {
        progressBar.hide()
        if(isAdded){
            parentFragmentManager.popBackStack()
        }
    }

    private fun setupBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        binding = FragmentAddExamBinding
            .inflate(inflater, container, false)
            .apply {
                val currentUser=
                    UsersRepository.instance.getCurrentUserAccount()
                val teacherInfoModel= currentUser?.accessToken?.let { TeacherModel.fromToken(it) }
                teacherInfoModel?.apply {
                    setSpinnerList(
                        classAndSectionSpinner,
                        classesInfo
                    )
                    setSpinnerList(
                        examTypeSpinner,
                        examType
                    )
                }

                dateField.setOnClickListener(::onDateClicked)
                classAndSectionSpinner.onItemSelectedListener = onClassSelected
                examTypeSpinner.onItemSelectedListener= onExamTypeSelected
            }
    }

    private fun onDateClicked(dateField: View) {
        DatePickerFragment.newInstance()
            .apply {
                onDateSet = {
                    (dateField as TextView).setTextFromDate(it)
                }

                this@AddExamFragment.fragmentManager?.let { show(it, "") }
            }
    }


    private fun setupViewModel() {
        viewModel.apply {
                binding.apply {
                    e= postException
                    postModel = postExamModel
                    lifecycleOwner= this@AddExamFragment
                    listener= this@AddExamFragment
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save_btn, menu)

        saveItem= menu.findItem(R.id.saveMenuItem)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.saveMenuItem -> {
                viewModel.addHomework()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onUploadCompleted() {
        onBackPressed()
    }

    override fun onUploadStarted() {
        saveItem.isVisible = false
        progressBar.show()
    }

    override fun onError(errorMsg: String) {
        progressBar.hide()
        saveItem.isVisible = true
        binding.constraintLayout.showSnackBar(errorMsg)
    }
}