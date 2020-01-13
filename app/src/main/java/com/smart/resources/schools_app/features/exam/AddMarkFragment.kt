package com.smart.resources.schools_app.features.exam

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.myTypes.*
import com.smart.resources.schools_app.databinding.FragmentAddMarkBinding
import com.smart.resources.schools_app.features.students.SendStudentResult
import com.smart.resources.schools_app.sharedUi.SectionActivity
import com.smart.resources.schools_app.features.students.Student
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class AddMarkFragment : Fragment() {
    private lateinit var binding: FragmentAddMarkBinding
    private lateinit var viewModel: FetchStudentViewModel
    private lateinit var adapter: AddMarkRecyclerAdapter
    private lateinit var adapter2: AddMarkRecyclerAdapter
    private var job:Job?=null

    companion object {
        private const val ADD_EXAM_FRAGMENT = "addExamFragment"
        var examId1 = -1
        fun newInstance(fm: FragmentManager, examId: Int) {

            examId1 = examId
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

        adapter = AddMarkRecyclerAdapter(listOf())
        binding.recyclerView.adapter = adapter

        setupViewModel(examId1)
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
                onSave()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onSave() {


       val studebtResult : SendStudentResult=SendStudentResult(AddMarkRecyclerAdapter.sendStudentResult,
           examId1)



        if(studebtResult.marks.isNotEmpty()) {

            job = CoroutineScope(IO).launch {
                val result =
                    GlobalScope.async { BackendHelper.examDao.addMultiResult(studebtResult) }
                        .toMyResult()



            }
        }
    }



    fun validate(st:SendStudentResult):Boolean{
        var x=true
        for(s in st.marks){
            if(s.mark>100 || s.mark<0){
                x=false
            }
        }
        return x
    }

    override fun onStop() {
        super.onStop()
        job?.cancel()
    }

    override fun onStart() {
        super.onStart()
        job?.start()
    }

    private fun setupViewModel(examId: Int) {
        viewModel = ViewModelProviders.of(this)
            .get(FetchStudentViewModel::class.java).apply {
                getStudents(examId).observe(
                    this@AddMarkFragment,
                    androidx.lifecycle.Observer { onExamsDownload(it) })
            }

    }


    private fun onExamsDownload(result: MyResult<List<Student>>) {
        var errorMsg = ""
        when (result) {
            is Success -> {
                if (result.data.isNullOrEmpty()) errorMsg = getString(R.string.no_exams)
                else {
                    binding.recyclerView.adapter = AddMarkRecyclerAdapter(result.data)
                }

            }
            is ResponseError -> errorMsg = result.combinedMsg
            is ConnectionError -> errorMsg = getString(R.string.connection_error)
        }
    }


}
