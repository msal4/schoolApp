package com.smart.resources.schools_app.features.ratingAdd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.core.bindingAdapters.textView.setDate
import com.smart.resources.schools_app.databinding.BottomSheetAddRatingBinding
import com.smart.resources.schools_app.features.rating.RatingModel
import com.smart.resources.schools_app.sharedUi.DatePickerFragment

// TODO: add scrolling when page opens
class AddRatingBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetAddRatingBinding
    private lateinit var ratingModel: RatingModel
    private var position: Int= 0
    var onRatingSet: ((Int, RatingModel)-> Unit)?= null

    companion object Factory {
        private const val EXTRA_ADD_RATING_MODEL= "extraAddRatingModel"
        private const val EXTRA_POSITION= "extraPosition"

        fun newInstance(
            ratingModel: RatingModel,
            position: Int
        ): AddRatingBottomSheet {
            val bottomSheet = AddRatingBottomSheet()
            val bundle = Bundle().apply {
                putParcelable(EXTRA_ADD_RATING_MODEL, ratingModel)
                putInt(EXTRA_POSITION, position)
            }

            bottomSheet.arguments = bundle
            return bottomSheet
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // TODO: check if working
        binding = BottomSheetAddRatingBinding.inflate(inflater, container, false)

        arguments?.apply {
            getParcelable<RatingModel>(EXTRA_ADD_RATING_MODEL)?.let { ratingModel= it }
            position= getInt(EXTRA_POSITION)
        }

        binding.ratingModel= ratingModel
        binding.dateField.setOnClickListener(::onDateClicked)

            binding
            .rateBtn
            .setOnClickListener {
                insertRating(it)
                dismiss()
            }
        return binding.root
    }

    private fun onDateClicked(dateField: View) {
        DatePickerFragment.newInstance()
            .apply {
                onDateSet = {
                    (dateField as TextView).setDate(it)
                }

                this@AddRatingBottomSheet.fragmentManager?.let { show(it, "") }
            }
    }

    private fun insertRating(it: View) {
        if(ratingModel.rate <0f) ratingModel.rate= 0f
        onRatingSet?.invoke(position, ratingModel)
        Logger.i(ratingModel.toString())
    }

}