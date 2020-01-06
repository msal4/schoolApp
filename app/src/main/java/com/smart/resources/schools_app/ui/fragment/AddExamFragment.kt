package com.smart.resources.schools_app.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.FragmentAddExamBinding
import com.smart.resources.schools_app.ui.activity.SectionActivity
import com.smart.resources.schools_app.util.toast

class AddExamFragment : Fragment() {
    private lateinit var binding: FragmentAddExamBinding

    companion object {
        private const val ADD_EXAM_FRAGMENT= "addExamFragment"

        fun newInstance(fm:FragmentManager){

            val fragment=  AddExamFragment()
            fm.beginTransaction().apply {
                setCustomAnimations(R.anim.slide_in_right,
                    R.anim.slide_out_left,R.anim.slide_in_left, R.anim.slide_out_right)
                replace(R.id.fragmentContainer, fragment)
                addToBackStack(ADD_EXAM_FRAGMENT)
                commit()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddExamBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        (activity as SectionActivity).setCustomTitle(R.string.add_exam)
        return binding.root
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
}
