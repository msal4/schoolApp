package com.smart.resources.schools_app.features.rating

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.widget.Toast
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
import com.smart.resources.schools_app.databinding.FragmentAddRatingBinding
import com.smart.resources.schools_app.features.profile.TeacherInfoModel
import com.smart.resources.schools_app.features.students.Student
import com.smart.resources.schools_app.sharedUi.SectionActivity
import com.tiper.MaterialSpinner


class AddRatingFragment : Fragment(), MaterialSpinner.OnItemSelectedListener,
    AddRatingAdapter.OnItemClickListener {
    private lateinit var binding: FragmentAddRatingBinding
    private lateinit var adapter: AddRatingAdapter
    private lateinit var viewModel: AddRatingViewModel


    companion object {
        private const val ADD_RATING_FRAGMENT= "addRatingFragment"

        fun newInstance(fm:FragmentManager){
                val fragment=
                    AddRatingFragment()
            fm.beginTransaction().apply {
                setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    R.anim.slide_in_left,
                    R.anim.slide_out_right)
                replace(R.id.fragmentContainer, fragment)
                addToBackStack(ADD_RATING_FRAGMENT)
                commit()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddRatingBinding
            .inflate(inflater, container, false)




        binding.classAndSectionSpinner.onItemSelectedListener=this

        TeacherInfoModel.instance?.classesInfo?.let {
            setSpinnerList(
                binding.classAndSectionSpinner,
                it
            )
        }

        //setHasOptionsMenu(true)
        (activity as SectionActivity).setCustomTitle(R.string.add_homework)
        return binding.root
    }



    private fun setupViewModel(classId:Int) {
        viewModel = ViewModelProviders.of(this)
            .get(AddRatingViewModel::class.java).apply {
                getRatings(classId).observe(this@AddRatingFragment, Observer{onRatingDownload(it)})
            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save_btn, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.saveMenuItem-> {
            }
        }

        return super.onOptionsItemSelected(item)
    }


    private  fun onRatingDownload(result: RatingResult1) {
        var errorMsg = ""
        when (result) {
            is Success -> {
                if (result.data.isNullOrEmpty()) errorMsg = getString(R.string.no_rating)
                else {
                    binding.recyclerView.adapter= AddRatingAdapter(result.data,this)
                }

            }
            is ResponseError -> errorMsg = result.combinedMsg
            is ConnectionError -> errorMsg = getString(R.string.connection_error)
        }

    }


    fun getClassId(className:String):Int{
        var x=-1
        var classInfo=TeacherInfoModel.instance?.classesInfo
        if (classInfo != null) {
            for( cl in classInfo){
                if(cl.getClassSection == className){
                    x=cl.classId
                    break
                }
            }
        }
        return x
    }


    override fun onItemSelected(parent: MaterialSpinner, view: View?, position: Int, id: Long) {
        setupViewModel(getClassId(parent.selectedItem.toString()))

        Toast.makeText(context,""+getClassId(parent.selectedItem.toString()),Toast.LENGTH_LONG).show()
    }

    override fun onNothingSelected(parent: MaterialSpinner) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemClick(examModel: Student) {
        AddRatingBottomSheet.newInstance()
    }


}