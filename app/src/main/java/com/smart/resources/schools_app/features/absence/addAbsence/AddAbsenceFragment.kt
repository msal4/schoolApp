package com.smart.resources.schools_app.features.absence.addAbsence

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.adapters.setSpinnerList
import com.smart.resources.schools_app.core.myTypes.ConnectionError
import com.smart.resources.schools_app.core.myTypes.ResponseError
import com.smart.resources.schools_app.core.myTypes.Success
import com.smart.resources.schools_app.core.utils.hide
import com.smart.resources.schools_app.core.utils.show
import com.smart.resources.schools_app.sharedUi.SectionActivity
import com.smart.resources.schools_app.core.utils.toast
import com.smart.resources.schools_app.databinding.FragmentAbsenceBinding
import com.smart.resources.schools_app.features.profile.ClassInfoModel
import com.smart.resources.schools_app.features.profile.TeacherInfoModel
import com.smart.resources.schools_app.features.students.Student
import com.tiper.MaterialSpinner

class AddAbsenceFragment : Fragment(), MaterialSpinner.OnItemSelectedListener {
    private lateinit var binding: FragmentAbsenceBinding
    private lateinit var adapter: AddAbsenceRecyclerAdapter
    private lateinit var viewModel: AddAbsenceViewModel

    companion object {
        private const val ADD_ABSENCE_FRAGMENT= "addAbsenceFragment"

        fun newInstance(fm:FragmentManager){

            val fragment=
                AddAbsenceFragment()
            fm.beginTransaction().apply {
                setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,R.anim.slide_in_left, R.anim.slide_out_right)
                replace(R.id.fragmentContainer, fragment)
                addToBackStack(ADD_ABSENCE_FRAGMENT)
                commit()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAbsenceBinding.inflate(inflater, container, false)
            .apply {
            TeacherInfoModel.instance?.apply {
                setSpinnerList(classAndSectionSpinner, classesInfo)
                classAndSectionSpinner.onItemSelectedListener= this@AddAbsenceFragment
                setSpinnerList(subjectsSpinner, subjects)
                adapter=
                    AddAbsenceRecyclerAdapter(::onStudentSelected)
                setupRecycler()
            }
        }

        viewModel= ViewModelProviders.of(this)
            .get(AddAbsenceViewModel::class.java)
            .apply {
            students.observe(this@AddAbsenceFragment, Observer {
                onDownloadCompleted(it)
            })
        }
        setHasOptionsMenu(true)
        (activity as SectionActivity).setCustomTitle(R.string.add_absence)
        return binding.root
    }

    private fun FragmentAbsenceBinding.setupRecycler() {
        recyclerViewLoader.apply {
            recyclerView.adapter = adapter
            progressIndicator.hide()
            errorText.text = getString(R.string.select_class)
        }
    }

    private fun onDownloadCompleted(studentsResult: studentResult){
        binding.recyclerViewLoader.apply {
            progressIndicator.hide()

            when(studentsResult){
                is Success -> {
                    if(studentsResult.data.isNullOrEmpty()) errorText.text= getString(R.string.no_students)
                    else  {
                        errorText.text= ""
                        adapter.updateData(studentsResult.data)
                    }
                }
                is ResponseError -> errorText.text= studentsResult.combinedMsg
                is ConnectionError -> errorText.text= getString(R.string.connection_error)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save_btn, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.saveMenuItem-> context?.toast("saved")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemSelected(parent: MaterialSpinner, view: View?, position: Int, id: Long) {
        if(parent.selectedItem is ClassInfoModel){
            binding.recyclerViewLoader.apply {
                progressIndicator.show()
                errorText.text= ""
            }

            val classId= (parent.selectedItem as ClassInfoModel).classId.toString()
            viewModel.getClassStudents(classId)
        }
    }

    override fun onNothingSelected(parent: MaterialSpinner) {
    }

     private fun onStudentSelected(student: Student, isChecked:Boolean) {
         viewModel.postAbsenceModel.studentsId.apply {
             if(isChecked){
                 add(student.idStudent)
             }else{
                 remove(student.idStudent)
             }
         }
    }
}
