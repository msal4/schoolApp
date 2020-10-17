package com.smart.resources.schools_app.features.library

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.core.activity.SectionActivity
import com.smart.resources.schools_app.core.extentions.*
import androidx.lifecycle.observe

class LibraryFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private val viewModel: LibraryViewModel by viewModels()

    companion object {
        fun newInstance(fm:FragmentManager){

            val fragment=
                LibraryFragment()

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
        binding = FragmentRecyclerLoaderBinding.inflate(inflater, container, false)
            .apply {
            lifecycleOwner= this@LibraryFragment
        }
        (activity as SectionActivity).setCustomTitle(R.string.library)

        setupViewModel()
        return binding.root
    }

    private fun setupViewModel() {
        viewModel.apply {
                binding.listState= listState
                getBooks().observe(viewLifecycleOwner, {onHomeworkDownload(it)})
            }
    }

    private  fun onHomeworkDownload(result: List<LibraryModel>) {
        binding.recyclerView.createGridLayout(LibraryRecyclerAdapter(result))

    }
}
