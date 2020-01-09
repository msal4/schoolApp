package com.smart.resources.schools_app.features.library

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.features.containerActivities.SectionActivity
import com.smart.resources.schools_app.core.util.*

class LibraryFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private lateinit var viewModel: LibraryViewModel

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
        (activity as SectionActivity).setCustomTitle(R.string.library)

        setupViewModel()
        return binding.root
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)
            .get(LibraryViewModel::class.java).apply {
                getBooks().observe(this@LibraryFragment, Observer{onHomeworkDownload(it)})
            }
    }

    private  fun onHomeworkDownload(result: LibraryResult) {
        var errorMsg = ""
        when (result) {
            is Success -> {
                if (result.data.isNullOrEmpty()) errorMsg = getString(R.string.no_library)
                else {
                    binding.recyclerView.createGridLayout(LibraryRecyclerAdapter(result.data))
                }
            }
            is ResponseError -> errorMsg = result.combinedMsg
            is ConnectionError -> errorMsg = getString(R.string.connection_error)
        }

        binding.errorText.text = errorMsg
        binding.progressIndicator.hide()
    }
}
