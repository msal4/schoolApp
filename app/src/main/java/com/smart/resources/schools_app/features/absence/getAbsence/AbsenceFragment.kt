package com.smart.resources.schools_app.features.absence.getAbsence

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.activity.SectionActivity
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.features.absence.addAbsence.AddAbsenceFragment
import com.smart.resources.schools_app.features.users.data.UserRepository
import kotlinx.coroutines.launch

class AbsenceFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private val viewModel: AbsenceViewModel by viewModels()

    companion object {
        fun newInstance(fm: FragmentManager) {

            val fragment =
                AbsenceFragment()

            fm.beginTransaction().apply {
                add(R.id.fragmentContainer, fragment)
                commit()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecyclerLoaderBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@AbsenceFragment
            listState = viewModel.listState

            viewModel.getAbsence().observe(viewLifecycleOwner, {
                if (it == null) return@observe
                errorText.text = ""
                binding.recyclerView.adapter = AbsenceRecyclerAdapter(it)
                }
            )
        }
        
        (activity as SectionActivity).setCustomTitle(R.string.absence)
        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        lifecycleScope.launch {
            if (UserRepository.instance.getCurrentUserAccount()?.userType == 1) {
                inflater.inflate(R.menu.menu_add_btn, menu)
            }
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addMenuItem -> fragmentManager?.let { AddAbsenceFragment.newInstance(it) }
        }
        return super.onOptionsItemSelected(item)
    }

}
