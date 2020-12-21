package com.smart.resources.schools_app.features.fees

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.activity.SectionActivity
import com.smart.resources.schools_app.databinding.FragmentFeesBinding


class FeesFragment: Fragment() {
    private val viewModel: FeesViewModel by viewModels()
    private lateinit var binding: FragmentFeesBinding
    private lateinit var adapter: FeesRecyclerAdapter

    companion object {
        fun newInstance(fm: FragmentManager) {
            val fragment = FeesFragment()
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
        binding = FragmentFeesBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@FeesFragment
        }
        (activity as SectionActivity).setCustomTitle(R.string.fees)

        setupAdapter()

        viewModel.fetchFees()
        viewModel.getFees().observe(viewLifecycleOwner) {
            binding.paid.text = it.paid.toString()
            binding.remaining.text = it.remaining.toString()
            binding.amount.text = it.amount.toString()
            adapter.updateData(it.details)
        }


        return binding.root
    }

    private fun setupAdapter() {
        FeesRecyclerAdapter()
            .apply {
                adapter = this
                binding.feesRecyclerView.adapter = this
            }
    }
}