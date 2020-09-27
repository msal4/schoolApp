package com.smart.resources.schools_app.features.advertising

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.sharedUi.activity.SectionActivity
import com.smart.resources.schools_app.sharedUi.activity.ImageViewerActivity

class AdvertisingFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private val viewModel: HomeworkViewModel by viewModels()

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
        viewModel.apply {
                binding.listState= listState
                getExams().observe(viewLifecycleOwner, {onHomeworkDownload(it)})
            }
    }

    private fun onImageClick(imageView: ImageView, imageUrl: String){
        activity?.let { ImageViewerActivity.newInstance(it,imageView, imageUrl) }
    }

    private  fun onHomeworkDownload(result: List<AdvertisingModel>) {
       binding.recyclerView.adapter= AdvertisingRecyclerAdapter(result, ::onImageClick)
    }
}
