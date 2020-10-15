package com.smart.resources.schools_app.features.homework.addHomework

//import id.zelory.compressor.Compressor
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.haytham.coder.extensions.*
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.bindingAdapters.loadImageUrl
import com.smart.resources.schools_app.core.bindingAdapters.setSpinnerList
import com.smart.resources.schools_app.core.bindingAdapters.textView.setDate
import com.smart.resources.schools_app.core.extentions.*
import com.smart.resources.schools_app.core.utils.FileUtils
import com.smart.resources.schools_app.core.myTypes.PostListener
import com.smart.resources.schools_app.databinding.FragmentAddHomeworkBinding
import com.smart.resources.schools_app.features.homework.HomeworkViewModel
import com.smart.resources.schools_app.features.profile.ClassInfoModel
import com.smart.resources.schools_app.features.users.data.TeacherModel
import com.smart.resources.schools_app.features.users.data.UserRepository
import com.smart.resources.schools_app.features.dateTimePickers.DatePickerFragment
import com.smart.resources.schools_app.core.activity.SectionActivity
import com.tiper.MaterialSpinner
import id.zelory.compressor.Compressor
import java.io.File

class AddHomeworkFragment : Fragment(), PostListener {
    private lateinit var binding: FragmentAddHomeworkBinding
    private lateinit var progressBar: ProgressBar
    private lateinit var saveItem: MenuItem
    private val viewModel: HomeworkViewModel by activityViewModels()

    private val onSpinnerItemSelected = object :
        MaterialSpinner.OnItemSelectedListener {
        override fun onItemSelected(
            parent: MaterialSpinner,
            view: View?,
            position: Int,
            id: Long
        ) {
            viewModel.postHomeworkModel.classId =
                (parent.selectedItem as ClassInfoModel).classId.toString()
        }

        override fun onNothingSelected(parent: MaterialSpinner) {
        }

    }

    companion object {
        private const val ADD_EXAM_FRAGMENT = "addExamFragment"

        fun newInstance(fm: FragmentManager) {
            val fragment =
                AddHomeworkFragment()
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
            setCustomTitle(R.string.add_homework)
            progressBar = getToolbarProgressBar()
            onBackPressedDispatcher
                .addCallback(viewLifecycleOwner, object :
                    OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        this@AddHomeworkFragment.onBackPressed()
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
        binding = FragmentAddHomeworkBinding
            .inflate(inflater, container, false).apply {
                val currentUser = UserRepository.instance.getCurrentUserAccount()
                val teacherInfoModel = currentUser?.accessToken?.let { TeacherModel.fromToken(it.value) }
                teacherInfoModel?.classesInfo
                    ?.let {
                        classAndSectionSpinner.setSpinnerList(it)
                    }

                dateField.setOnClickListener(::onDateClicked)
                classAndSectionSpinner.onItemSelectedListener = onSpinnerItemSelected
                addImageIcon.setOnClickListener(::addImage)
            }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GET_IMAGE_REQUEST &&
            resultCode == Activity.RESULT_OK &&
            data != null
        ) {
            data.getImage().let {
                saveAsCompressedFile(it)

                binding.apply {
                    loadImageUrl(
                        homeworkImage,
                        it.toString()
                    )
                    homeworkImageCard.visibility = View.VISIBLE
                    addImageIcon.imageTintList =
                        context?.let { c ->
                            ContextCompat.getColorStateList(
                                c,
                                R.color.shadowColor
                            )
                        }
                }
            }
        }
    }

    private fun saveAsCompressedFile(it: Uri) {
        val originalFile: File = FileUtils.from(context, it)
        val imageFile = Compressor(context).compressToFile(originalFile)
        viewModel.postHomeworkModel.attachment = imageFile
    }

    private fun addImage(view: View) {
        openImagePickerApp()
    }

    private fun onDateClicked(dateField: View) {
        DatePickerFragment.newInstance()
            .apply {
                onDateSet = {
                    (dateField as TextView).setDate(it)
                }

                this@AddHomeworkFragment.fragmentManager?.let { show(it, "") }
            }
    }


    private fun setupViewModel() {
        viewModel.apply {
                binding.apply {
                    e = postException
                    postModel = postHomeworkModel
                    lifecycleOwner = this@AddHomeworkFragment
                    uploadListener = this@AddHomeworkFragment
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