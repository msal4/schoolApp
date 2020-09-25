package com.smart.resources.schools_app.features.ratingAdd

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.bindingAdapters.setSpinnerList
import com.smart.resources.schools_app.core.myTypes.PostListener
import com.smart.resources.schools_app.core.extentions.hide
import com.smart.resources.schools_app.core.extentions.show
import com.smart.resources.schools_app.core.extentions.showSnackBar
import com.smart.resources.schools_app.databinding.FragmentAddRatingBinding
import com.smart.resources.schools_app.features.absence.addAbsence.AddAbsenceViewModel
import com.smart.resources.schools_app.features.users.UsersRepository
import com.smart.resources.schools_app.features.profile.ClassInfoModel
import com.smart.resources.schools_app.features.profile.TeacherModel
import com.smart.resources.schools_app.features.rating.RatingModel
import com.smart.resources.schools_app.sharedUi.SectionActivity
import com.tiper.MaterialSpinner


class AddRatingFragment : Fragment(), MaterialSpinner.OnItemSelectedListener, PostListener {
    private lateinit var binding: FragmentAddRatingBinding
    private lateinit var adapter: AddRatingAdapter
    private lateinit var saveMenuItem: MenuItem
    private lateinit var toolbarProgressBar: ProgressBar
    private val viewModel: AddRatingViewModel by activityViewModels {
        AddRatingViewModel.Factory(requireActivity().application, this)
    }

    companion object {
        fun newInstance(fm:FragmentManager){
                val fragment=
                    AddRatingFragment()
            fm.beginTransaction().apply {
                setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    R.anim.slide_in_left,
                    R.anim.slide_out_right)
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
            setCustomTitle(R.string.add_rating)
            toolbarProgressBar= getToolbarProgressBar()
        }
        return binding.root
    }

    private fun setupBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        binding = FragmentAddRatingBinding
            .inflate(inflater, container, false).apply {
                val currentUser= UsersRepository.instance.getCurrentUserAccount()
                val teacherInfoModel= currentUser?.accessToken?.let { TeacherModel.fromToken(it) }
                teacherInfoModel?.let {
                    setSpinnerList(
                        classAndSectionSpinner,
                        it.classesInfo
                    )
                    classAndSectionSpinner.onItemSelectedListener = this@AddRatingFragment
                }


                adapter = AddRatingAdapter(::onRecyclerItemClicked)
                recyclerViewLoader.recyclerView.adapter = adapter

                lifecycleOwner = this@AddRatingFragment
            }
    }

    private fun setupViewModel() {
        viewModel.apply {
                binding.viewState = listState
                binding.sectionsError= sectionAndClassesErrorMsg
                ratingModels.observe(viewLifecycleOwner, ::onStudentsDownloadCompleted)

            }
    }

    private fun onStudentsDownloadCompleted(students: MutableList<RatingModel>?){
        students?.let {
            adapter.updateData(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save_btn, menu)
        saveMenuItem= menu.findItem(R.id.saveMenuItem)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.saveMenuItem-> {
                viewModel.addRatings()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun onRecyclerItemClicked(ratingModel: RatingModel, position: Int ){
        fragmentManager?.let {fm ->
           AddRatingBottomSheet.newInstance(ratingModel, position).also {
               it.show(fm, "")
               it.onRatingSet= ::onRatingSet
           }
        }
    }

    override fun onItemSelected(parent: MaterialSpinner, view: View?, position: Int, id: Long) {
        if(parent.selectedItem is ClassInfoModel){
            adapter.clearData()
            val classId= (parent.selectedItem as ClassInfoModel).classId.toString()
            viewModel.classId.value=  classId
        }
    }

    override fun onNothingSelected(parent: MaterialSpinner) {
    }

    override fun onUploadCompleted() {
        setToolbarLoading(false)
        adapter.resetAll()
        binding.addRatingLayout.showSnackBar(getString(R.string.done_successfully), false)
    }

    override fun onUploadStarted() {
        setToolbarLoading(true)
    }

    override fun onError(errorMsg: String) {
        setToolbarLoading(false)
        binding.addRatingLayout.showSnackBar(errorMsg)
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

    private fun onRatingSet(position:Int, ratingModel: RatingModel){
        adapter.updateItem(position, ratingModel)
    }
}