package com.smart.resources.schools_app.features.rating

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.sharedUi.SectionActivity

class RatingFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private val viewModel: RatingViewModel by viewModels()

    companion object {
        fun newInstance(fm:FragmentManager){

            val fragment=
                RatingFragment()

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
                lifecycleOwner= this@RatingFragment
            }
        (activity as SectionActivity).setCustomTitle(R.string.rating)
        setHasOptionsMenu(true)

        setupViewModel()
        return binding.root
    }

    private fun setupViewModel() {
        viewModel.apply {
                binding.listState= listState
                getRatings().observe(viewLifecycleOwner, {onHomeworkDownload(it)})
            }
    }


    private  fun onHomeworkDownload(result: List<RatingModel>) {
        binding.recyclerView.adapter= RatingAdapter(result)
    }
}
