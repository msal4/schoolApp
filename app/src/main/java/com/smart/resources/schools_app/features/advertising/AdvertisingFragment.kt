package com.smart.resources.schools_app.features.advertising

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.myTypes.ConnectionError
import com.smart.resources.schools_app.core.myTypes.ResponseError
import com.smart.resources.schools_app.core.myTypes.Success
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.sharedUi.SectionActivity
import com.smart.resources.schools_app.core.utils.*

class AdvertisingFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private lateinit var viewModel: HomeworkViewModel

    companion object {
        fun newInstance(fm:FragmentManager){

            val fragment=
                AdvertisingFragment()

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
            lifecycleOwner= this@AdvertisingFragment
        }
        (activity as SectionActivity).setCustomTitle(R.string.advertising)

        setupViewModel()
        return binding.root
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)
            .get(HomeworkViewModel::class.java).apply {
                binding.listState= listState
                getExams().observe(this@AdvertisingFragment, Observer{onHomeworkDownload(it)})
            }
    }


    private  fun onHomeworkDownload(result: List<AdvertisingModel>) {
       binding.recyclerView.adapter= AdvertisingRecyclerAdapter(result)
    }
}
