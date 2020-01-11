package com.smart.resources.schools_app.features.rating

import android.app.Activity
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.adapters.setSpinnerList
import com.smart.resources.schools_app.databinding.FragmentAddRatingBinding
import com.smart.resources.schools_app.features.profile.TeacherInfoModel
import com.smart.resources.schools_app.sharedUi.SectionActivity


class AddRatingFragment : Fragment(), GetData {
    private lateinit var binding: FragmentAddRatingBinding
    private lateinit var adapter: AddRatingAdapter
    private var callback: GetData? = null

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



        callback=this
        adapter= fragmentManager?.let {
            AddRatingAdapter(listOf(), callback as AddRatingFragment,
                it
            )
        }!!
        binding.recyclerView.adapter=adapter
        TeacherInfoModel.instance?.classesInfo?.let {
            setSpinnerList(
                binding.classAndSectionSpinnerAmmar,
                it
            )
        }

        //setHasOptionsMenu(true)
        (activity as SectionActivity).setCustomTitle(R.string.add_homework)
        return binding.root
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

    override fun callbackMethod(notes: String?, rating: Int) {

    }


}