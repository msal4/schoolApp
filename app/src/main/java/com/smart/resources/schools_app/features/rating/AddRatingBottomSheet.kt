package com.smart.resources.schools_app.features.rating

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.databinding.BottomSheetAddRatingBinding
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

class AddRatingBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetAddRatingBinding
    private var numOfItems:Int= 1

    companion object Factory {
        fun newInstance(

        ): AddRatingBottomSheet {
            val bottomSheet =
                AddRatingBottomSheet()
            val bundle = Bundle()

            bottomSheet.arguments = bundle
            return bottomSheet
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetAddRatingBinding.inflate(layoutInflater, container, false)

        setListeners()

        Logger.e("Hello otachy")
        binding
            .addBtn
            .setOnClickListener {
                insertRating(it)
                dismiss()
            }
        return binding.root
    }

    private fun setListeners() {
        binding.increaseBtn.setOnClickListener {
            numOfItems += 1
            if (numOfItems > 5) numOfItems = 5
        }

        binding.decreaseBtn.setOnClickListener {
            numOfItems -= 1
            if (numOfItems < 1) numOfItems = 1


        }


    }


    private fun insertRating(it: View) {


    }

}