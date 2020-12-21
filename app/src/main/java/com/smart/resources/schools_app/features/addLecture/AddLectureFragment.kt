package com.smart.resources.schools_app.features.addLecture

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.activity.SectionActivity
import com.smart.resources.schools_app.core.bindingAdapters.setSelectedItem
import com.smart.resources.schools_app.core.bindingAdapters.setSpinnerList
import com.smart.resources.schools_app.core.myTypes.Success
import com.smart.resources.schools_app.core.network.RetrofitHelper
import com.smart.resources.schools_app.databinding.AddLectureFragmentBinding
import com.smart.resources.schools_app.features.users.data.model.userInfo.TeacherInfoModel
import com.smart.resources.schools_app.features.users.data.repository.UserRepository
import com.tiper.MaterialSpinner
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import java.io.FileNotFoundException
import java.io.InputStream

class AddLectureFragment : Fragment() {
    private lateinit var binding: AddLectureFragmentBinding

    // Request code for selecting a PDF document.
    private val PICK_PDF_FILE = 2
    private var pdfFile: InputStream? = null
    private var subjectId: Int? = null

    companion object {
        fun newInstance(fm: FragmentManager) {
            val fragment = AddLectureFragment()
            fm.beginTransaction().apply {
                add(R.id.fragmentContainer, fragment)
                commit()
            }
        }
    }

    private val viewModel: AddLectureViewModel by viewModels()

    private val onClassSelectedListener = object : MaterialSpinner.OnItemSelectedListener {
        override fun onItemSelected(parent: MaterialSpinner, view: View?, position: Int, id: Long) {
            viewModel.fetchSubjects(parent.selectedItem.toString())
        }

        override fun onNothingSelected(parent: MaterialSpinner) {}
    }

    private val onSubjectSelectedListener = object : MaterialSpinner.OnItemSelectedListener {
        override fun onItemSelected(parent: MaterialSpinner, view: View?, position: Int, id: Long) {
            viewModel.subjects.value?.let {
                subjectId = if (position >= 0) it[position].idSubject else null
            }
        }

        override fun onNothingSelected(parent: MaterialSpinner) {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddLectureFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@AddLectureFragment
        }

        (activity as SectionActivity).setCustomTitle(R.string.add_lecture)

        binding.selectPdf.setOnClickListener(::openFile)
        binding.removePdf.setOnClickListener(::removeFile)
        binding.submit.setOnClickListener(::submit)

        lifecycleScope.launch {
            UserRepository.instance.getUserModel()?.let { teacher ->
                when (teacher) {
                    is TeacherInfoModel -> {
                        val adapter = ArrayAdapter<String>(
                            requireContext(),
                            R.layout.support_simple_spinner_dropdown_item
                        )
                        adapter.addAll(teacher.classesInfo.map { it.className }.toSet())
                        binding.selectClass.adapter = adapter
                        binding.selectClass.onItemSelectedListener = onClassSelectedListener
                        binding.selectSubject.onItemSelectedListener = onSubjectSelectedListener
                        viewModel.subjects.observe(viewLifecycleOwner) { list ->
                            binding.selectSubject.hint =
                                if (list.isNullOrEmpty()) "لا توجد مواد" else "المادة"
                            binding.selectSubject.setSelectedItem(-1)
                            binding.selectSubject.adapter = null
                            binding.selectSubject.setSpinnerList(list.map { it.subjectName })
                        }
                    }
                    else -> {
                    }
                }
            }
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_PDF_FILE) {
            data?.data?.also {
                setPdfFile(
                    try {
                        context?.contentResolver?.openInputStream(it)
                    } catch (e: FileNotFoundException) {
                        null
                    }
                )
            }
        }
    }

    private fun openFile(view: View?) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
        }

        startActivityForResult(intent, PICK_PDF_FILE)
    }

    private fun submit(view: View?) {
        val url = binding.lectureUrlText.text.toString()
        val lectureSubject = binding.lectureSubjectText.toString()

        if (subjectId == null) {
            Timber.d("subjectId is null for some reason")
            return
        }

        binding.submit.isEnabled = false

        lifecycleScope.launch {


            val res = RetrofitHelper.addLectureClient.addLecture(
                subjectId = subjectId!!,
                lecture = lectureSubject,
                pdfURL = makePartBody(),
                url = url
            )

            when (res) {
                is Success -> {
                    Timber.d("this is working perfectly")
                    activity?.supportFragmentManager?.popBackStackImmediate()
                }
                else -> binding.submit.isEnabled = true
            }
        }
    }

    private fun removeFile(view: View?) {
        setPdfFile(null)
    }

    private fun setPdfFile(file: InputStream?) {
        pdfFile = file
        binding.removePdf.apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                if (file == null) 0.0f else 0.25f
            )
            visibility = if (file == null) View.GONE else View.VISIBLE
        }

        binding.selectPdf.apply {
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                if (file == null) 1.0f else 0.75f
            )
            params.setMargins(0, 0, if (file == null) 0 else 10, 0)
            layoutParams = params
        }
    }


    private fun makePartBody(): MultipartBody.Part? {
        if (pdfFile == null) return null
        val bytes = pdfFile!!.readBytes()
        return MultipartBody.Part.createFormData(
            "pdfURL",
            "file.pdf",
            bytes.toRequestBody("application/pdf".toMediaTypeOrNull(), 0, bytes.size)
        )
    }
}
