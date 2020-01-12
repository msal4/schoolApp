package com.smart.resources.schools_app.features.exam

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.adapters.dateTimeBackendFormatter
import com.smart.resources.schools_app.features.profile.ClassInfoModel
import com.smart.resources.schools_app.core.adapters.setSpinnerList
import com.smart.resources.schools_app.core.myTypes.ConnectionError
import com.smart.resources.schools_app.core.myTypes.MyResult
import com.smart.resources.schools_app.core.myTypes.ResponseError
import com.smart.resources.schools_app.core.myTypes.Success
import com.smart.resources.schools_app.core.utils.hide
import com.smart.resources.schools_app.core.utils.toast
import com.smart.resources.schools_app.databinding.FragmentAddExamBinding
import com.smart.resources.schools_app.databinding.FragmentAddMarkBinding
import com.smart.resources.schools_app.sharedUi.SectionActivity
import com.smart.resources.schools_app.features.profile.TeacherInfoModel
import com.smart.resources.schools_app.features.rating.AddRatingAdapter
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import java.util.*

class AddMarkFragment : Fragment() {
    private lateinit var binding: FragmentAddMarkBinding
    private lateinit var viewModel: ExamViewModel
    private lateinit var adapter: AddMarkRecyclerAdapter

    companion object {
        private const val ADD_EXAM_FRAGMENT = "addExamFragment"

        fun newInstance(fm: FragmentManager) {

            val fragment =
                AddMarkFragment()
            fm.beginTransaction().apply {
                setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right
                )
                replace(R.id.fragmentContainer, fragment)
                addToBackStack(ADD_EXAM_FRAGMENT)
                commit()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddMarkBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        adapter= AddMarkRecyclerAdapter(listOf())
        binding.recyclerView.adapter=adapter

        setupViewModel()
        (activity as SectionActivity).setCustomTitle(R.string.add_exam)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save_btn, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.saveMenuItem -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }



    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)
            .get(ExamViewModel::class.java).apply {
                getExams().observe(this@AddMarkFragment, androidx.lifecycle.Observer { onExamsDownload(it) })
            }

    }


    private  fun onExamsDownload(result: MyResult<List<ExamModel>>) {
        var errorMsg = ""
        when (result) {
            is Success -> {
                if (result.data.isNullOrEmpty()) errorMsg = getString(R.string.no_exams)
                else {
                    binding.recyclerView.adapter= AddMarkRecyclerAdapter(result.data)
                }

            }
            is ResponseError -> errorMsg = result.combinedMsg
            is ConnectionError -> errorMsg = getString(R.string.connection_error)
        }
    }


}
