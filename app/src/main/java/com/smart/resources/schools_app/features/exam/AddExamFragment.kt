package com.smart.resources.schools_app.features.exam

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.ClassInfoModel
import com.smart.resources.schools_app.core.setSpinnerList
import com.smart.resources.schools_app.core.util.*
import com.smart.resources.schools_app.databinding.FragmentAddExamBinding
import com.smart.resources.schools_app.features.containerActivities.SectionActivity
import com.smart.resources.schools_app.features.profile.TeacherInfoModel
import org.threeten.bp.LocalDateTime
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class AddExamFragment : Fragment() {
    private lateinit var binding: FragmentAddExamBinding
    private lateinit var viewModel: AddExamViewModel

    companion object {
        private const val ADD_EXAM_FRAGMENT= "addExamFragment"

        fun newInstance(fm:FragmentManager){

            val fragment=
                AddExamFragment()
            fm.beginTransaction().apply {
                setCustomAnimations(R.anim.slide_in_right,
                    R.anim.slide_out_left,R.anim.slide_in_left, R.anim.slide_out_right)
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
        binding = FragmentAddExamBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)


        TeacherInfoModel.instance?.classesInfo?.let {
            setSpinnerList(binding.ClassAndSectionSpinner,
                it
            )
        }


        binding.dateExam.setOnClickListener{

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd = context?.let {
                DatePickerDialog(
                    it,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                        // Display Selected date in textbox
                        binding.dateExam.setText(""+year + "-" + (monthOfYear+1) + "-" + dayOfMonth)
                    },
                    year,
                    month,
                    day
                )
            }

            dpd?.show()
        }
        setupViewModel()
        (activity as SectionActivity).setCustomTitle(R.string.add_exam)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save_btn, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.saveMenuItem-> {


                onSave()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun onSave(){

        val classinf=binding.ClassAndSectionSpinner.selectedItem as ClassInfoModel

        val  examPostModel=ExamPostModel(classinf.classId,binding.subjectName.text.toString(),
            LocalDateTime.parse(binding.dateExam.text.toString()),binding.note.text.toString(),binding.type.toString())

        viewModel.addExams(examPostModel)


    }



    private fun setupViewModel() {

        viewModel = ViewModelProviders.of(this)
            .get(AddExamViewModel::class.java)



    }


}
