package com.smart.resources.schools_app.features.absence.addAbsence

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.adapters.setSpinnerList
import com.smart.resources.schools_app.core.myTypes.PostListener
import com.smart.resources.schools_app.core.utils.hide
import com.smart.resources.schools_app.core.utils.show
import com.smart.resources.schools_app.core.utils.showSnackBar
import com.smart.resources.schools_app.sharedUi.SectionActivity
import com.smart.resources.schools_app.databinding.FragmentAddAbsenceBinding
import com.smart.resources.schools_app.features.absence.AddAbsenceModel
import com.smart.resources.schools_app.features.profile.ClassInfoModel
import com.smart.resources.schools_app.features.profile.TeacherInfoModel
import com.tiper.MaterialSpinner

class AddAbsenceFragment : Fragment(), MaterialSpinner.OnItemSelectedListener, PostListener {
    private lateinit var binding: FragmentAddAbsenceBinding
    private lateinit var adapter: AddAbsenceRecyclerAdapter
    private lateinit var viewModel: AddAbsenceViewModel
    private lateinit var toolbarProgressBar: ProgressBar
    private lateinit var saveMenuItem: MenuItem

    companion object {

        fun newInstance(fm:FragmentManager){

            val fragment=
                AddAbsenceFragment()
                fm.beginTransaction().apply {
                setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,R.anim.slide_in_left, R.anim.slide_out_right)
                add(R.id.fragmentContainer, fragment)
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
            setCustomTitle(R.string.add_absence)
            toolbarProgressBar= getToolbarProgressBar()
        }
        return binding.root
    }



    private fun setupBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        FragmentAddAbsenceBinding.inflate(inflater, container, false)
            .apply {
                binding = this

                // setup recycler
                recyclerViewLoader.apply {
                    adapter= AddAbsenceRecyclerAdapter()
                    recyclerView.adapter = adapter
                }

                // setup spinner
                TeacherInfoModel.instance?.let {
                    setSpinnerList(classAndSectionSpinner, it.classesInfo)
                    classAndSectionSpinner.onItemSelectedListener = this@AddAbsenceFragment
                    setSpinnerList(subjectsSpinner, it.subjects)
                    subjectsSpinner.onItemSelectedListener = this@AddAbsenceFragment
                }

                // set lifecycle owner
                lifecycleOwner = this@AddAbsenceFragment
            }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, AddAbsenceViewModel.Factory(activity!!.application, this))
            .get(AddAbsenceViewModel::class.java)
            .apply {
                binding.viewState = listState
                binding.exception= addAbsenceException
                addAbsenceModels.observe(this@AddAbsenceFragment, ::onStudentsDownloadCompleted)
            }
    }


    private fun onStudentsDownloadCompleted(students: List<AddAbsenceModel>?){
        students?.let {
           adapter.updateData(it)
        }
    }

    override fun onUploadCompleted(){
        setToolbarLoading(false)
        adapter.uncheckAll()
        binding.linearLayout.showSnackBar(getString(R.string.done_successfully), false)
    }

    override fun onUploadStarted() {
        setToolbarLoading(true)
    }

    private fun setToolbarLoading(isLoading: Boolean) {
        if(isLoading){
            toolbarProgressBar.show()
            saveMenuItem.isVisible = false
        }else{
            toolbarProgressBar.hide()
            saveMenuItem.isVisible = true
        }
    }

    override fun onError(errorMsg:String){
        setToolbarLoading(false)
        binding.linearLayout.showSnackBar(errorMsg)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save_btn, menu)
        saveMenuItem= menu.findItem(R.id.saveMenuItem)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.saveMenuItem->{
                viewModel.addAbsences()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemSelected(parent: MaterialSpinner, view: View?, position: Int, id: Long) {
        if(parent.selectedItem is ClassInfoModel){
            adapter.clearData()
            val classId= (parent.selectedItem as ClassInfoModel).classId.toString()
            viewModel.classId.value=  classId
        }else if(parent.selectedItem is String){
            viewModel.postAbsenceModel.subjectName= parent.selectedItem as String
        }
    }

    override fun onNothingSelected(parent: MaterialSpinner) {

    }

}
