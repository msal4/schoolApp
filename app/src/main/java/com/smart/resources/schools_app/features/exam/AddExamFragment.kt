package com.smart.resources.schools_app.features.exam

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.haytham.coder.extensions.hide
import com.haytham.coder.extensions.show
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.activity.SectionActivity
import com.smart.resources.schools_app.core.bindingAdapters.setSpinnerList
import com.smart.resources.schools_app.core.bindingAdapters.textView.setDate
import com.smart.resources.schools_app.core.extentions.showSnackBar
import com.smart.resources.schools_app.core.myTypes.PostListener
import com.smart.resources.schools_app.databinding.FragmentAddExamBinding
import com.smart.resources.schools_app.features.dateTimePickers.DatePickerFragment
import com.smart.resources.schools_app.features.profile.ClassInfoModel
import com.smart.resources.schools_app.features.users.data.model.userInfo.TeacherInfoModel
import com.smart.resources.schools_app.features.users.data.repository.UserRepository
import com.tiper.MaterialSpinner
import kotlinx.coroutines.launch

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

        override fun onNothingSelected(parent: MaterialSpinner) {}

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

        override fun onNothingSelected(parent: MaterialSpinner) {}

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
        if (isAdded) {
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
                lifecycleScope.launch {
                    val currentUser = UserRepository.instance.getCurrentAccount()
                    val teacherInfoModel = currentUser?.accessToken?.let { TeacherInfoModel.fromToken(it.value) }
                    teacherInfoModel?.apply {
                        classAndSectionSpinner.setSpinnerList(classesInfo)
                        examTypeSpinner.setSpinnerList(examType)
                    }
                }

                dateField.setOnClickListener(::onDateClicked)
                classAndSectionSpinner.onItemSelectedListener = onClassSelected
                examTypeSpinner.onItemSelectedListener = onExamTypeSelected
            }
    }

    private fun onDateClicked(dateField: View) {
        DatePickerFragment.newInstance()
            .apply {
                onDateSet = { (dateField as TextView).setDate(it) }
            }.show(parentFragmentManager, "")
    }

    private fun setupViewModel() {
        viewModel.apply {
            binding.apply {
                e = postException
                postModel = postExamModel
                lifecycleOwner = this@AddExamFragment
                listener = this@AddExamFragment
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save_btn, menu)

        saveItem = menu.findItem(R.id.saveMenuItem)
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