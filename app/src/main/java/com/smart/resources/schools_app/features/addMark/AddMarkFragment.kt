package com.smart.resources.schools_app.features.addMark

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.haytham.coder.extensions.hide
import com.haytham.coder.extensions.show
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.activity.SectionActivity
import com.smart.resources.schools_app.core.myTypes.ConnectionError
import com.smart.resources.schools_app.core.myTypes.ResponseError
import com.smart.resources.schools_app.core.myTypes.Success
import com.smart.resources.schools_app.core.myTypes.toMyResult
import com.smart.resources.schools_app.core.network.RetrofitHelper
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.features.students.models.SendStudentResult
import com.smart.resources.schools_app.features.students.models.StudentWithMark
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.observe
import com.smart.resources.schools_app.core.extentions.showSnackBar

class AddMarkFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private lateinit var adapter: AddMarkRecyclerAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var saveItem: MenuItem
    private val viewModel: AddMarkViewModel by viewModels()

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
                    R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right,
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
            lifecycleOwner = this@AddMarkFragment
        }
        setHasOptionsMenu(true)

        adapter =
            AddMarkRecyclerAdapter(
                listOf()
            )
        binding.recyclerView.adapter = adapter

        setupViewModel(examId1)
        (activity as SectionActivity).apply {
            setCustomTitle(R.string.add_mark)

            progressBar = getToolbarProgressBar()
            onBackPressedDispatcher
                .addCallback(viewLifecycleOwner, object :
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
        if (isAdded) {
            parentFragmentManager.popBackStack()
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
                onSave()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onSave() {


        val studentResult = SendStudentResult(
            AddMarkRecyclerAdapter.sendStudentResult,
            examId1
        )


        if (studentResult.marks.isNotEmpty()) {

            if (validateMark(studentResult)) {
                lifecycleScope.launch {
                    withContext(Main) {
                        setToolbarLoading(true)


                        val result =
                            GlobalScope.async {
                                RetrofitHelper.examClient.addMultiResult(
                                    studentResult
                                )
                            }
                                .toMyResult()


                        when (result) {
                            is Success -> {
                                onBackPressed()
                            }
                            is ResponseError -> {
                                showErrorMsg(result.combinedMsg)
                            }
                            is ConnectionError -> {
                                context?.getString(R.string.connection_error)
                                    ?.let { showErrorMsg(it) }

                            }
                        }

                        setToolbarLoading(false)

                    }

                }
            } else {
                binding.layout.showSnackBar("يجب ادخال درجة صحيحة")
            }

        } else {
            showErrorMsg(getString(R.string.no_degree_added))
        }

    }


    private fun showErrorMsg(msg: String) {
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

    private fun validateMark(st: SendStudentResult): Boolean {
        var x = true
        for (s in st.marks) {
            if (s.mark > 100 || s.mark < 0) {
                x = false
            }
        }
        return x
    }


    private fun setupViewModel(examId: Int) {
        viewModel.apply {
            binding.listState = listState
            getStudents(examId).observe(
                viewLifecycleOwner,
                { onExamsDownload(it) })
        }

    }

    private fun onExamsDownload(result: List<StudentWithMark>) {
        binding.recyclerView.adapter =
            AddMarkRecyclerAdapter(
                result
            )
    }


}
