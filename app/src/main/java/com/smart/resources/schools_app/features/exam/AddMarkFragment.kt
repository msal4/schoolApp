package com.smart.resources.schools_app.features.exam

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.helpers.BackendHelper
import com.smart.resources.schools_app.core.myTypes.*
import com.smart.resources.schools_app.core.utils.hide
import com.smart.resources.schools_app.core.utils.show
import com.smart.resources.schools_app.core.utils.showSnackBar
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.features.students.SendStudentResult
import com.smart.resources.schools_app.features.students.Student
import com.smart.resources.schools_app.sharedUi.SectionActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class AddMarkFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private lateinit var viewModel: AddMarkViewModel
    private lateinit var adapter: AddMarkRecyclerAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var saveItem: MenuItem

    private var job: Job? = null

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
        binding = FragmentRecyclerLoaderBinding.inflate(inflater, container, false).apply {
            lifecycleOwner= this@AddMarkFragment
        }
        setHasOptionsMenu(true)

        adapter = AddMarkRecyclerAdapter(listOf())
        binding.recyclerView.adapter = adapter

        setupViewModel(examId1)
        (activity as SectionActivity).apply {
            setCustomTitle(R.string.add_exam)

            progressBar = getToolbarProgressBar()
            onBackPressedDispatcher
                .addCallback(this@AddMarkFragment, object :
                    OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        this@AddMarkFragment.onBackPressed()
                    }
                })
        }
        return binding.root
    }


    private fun onBackPressed() {
        progressBar.hide()
        fragmentManager?.popBackStack()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu_save_btn, menu)
        saveItem = menu.findItem(R.id.saveMenuItem)
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


        val studebtResult = SendStudentResult(
            AddMarkRecyclerAdapter.sendStudentResult,
            examId1
        )


        if (studebtResult.marks.isNotEmpty()){

            if (validateMark(studebtResult)) {
                job = CoroutineScope(IO).launch {
                    withContext(Main) {
                        setToolbarLoading(true)


                        val result =
                            GlobalScope.async { BackendHelper.examDao.addMultiResult(studebtResult) }
                                .toMyResult()


                        when (result) {
                            is Success -> {
                                onBackPressed()

                            }
                            is ResponseError -> {
                                showErrorMsg(result.combinedMsg)
                            }
                            is ConnectionError -> {
                                context?.getString(R.string.connection_error)?.let { showErrorMsg(it) }


                            }


                        }

                        setToolbarLoading(false)

                    }

                }
            } else {
                binding.layout.showSnackBar("يجب ادخال درجة صحيحة")
            }

        }else{
            showErrorMsg(getString(R.string.no_dgree_added))
        }

    }


    private fun showErrorMsg(msg:String){
        setToolbarLoading(false)
        binding.layout.showSnackBar(msg)
    }
    private fun setToolbarLoading(isLoading: Boolean) {
        if (isLoading) {
            progressBar.show()
            saveItem.isVisible = false
        } else {
            progressBar.hide()
            saveItem.isVisible = true
        }
    }

    fun validateMark(st: SendStudentResult): Boolean {
        var x = true
        for (s in st.marks) {
            if (s.mark > 100 || s.mark < 0) {
                x = false
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
            .get(AddMarkViewModel::class.java).apply {
                binding.listState= listState
                getStudents(examId).observe(
                    this@AddMarkFragment,
                    androidx.lifecycle.Observer { onExamsDownload(it) })
            }

    }

    private fun onExamsDownload(result: List<Student>) {
        binding.recyclerView.adapter = AddMarkRecyclerAdapter(result)
    }


}
