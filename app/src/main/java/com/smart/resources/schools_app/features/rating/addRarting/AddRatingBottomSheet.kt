package com.smart.resources.schools_app.features.rating.addRarting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.core.adapters.setTextFromDate
import com.smart.resources.schools_app.databinding.BottomSheetAddRatingBinding
import com.smart.resources.schools_app.features.rating.AddRatingModel
import com.smart.resources.schools_app.sharedUi.DatePickerFragment
import org.threeten.bp.LocalDateTime

class AddRatingBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetAddRatingBinding
    private lateinit var addRatingModel: AddRatingModel
    private var position: Int= 0
    var onRatingSet: ((Int, AddRatingModel)-> Unit)?= null

    companion object Factory {
        private const val EXTRA_ADD_RATING_MODEL= "extraAddRatingModel"
        private const val EXTRA_POSITION= "extraPosition"

        fun newInstance(
            addRatingModel: AddRatingModel,
            position: Int
        ): AddRatingBottomSheet {
            val bottomSheet = AddRatingBottomSheet()
            val bundle = Bundle().apply {
                putParcelable(EXTRA_ADD_RATING_MODEL, addRatingModel)
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
        binding = BottomSheetAddRatingBinding.inflate(layoutInflater, container, false)

        arguments?.apply {
            getParcelable<AddRatingModel>(EXTRA_ADD_RATING_MODEL)?.let { addRatingModel= it }
            position= getInt(EXTRA_POSITION)
        }

        binding.ratingModel= addRatingModel
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
                onDateSet = { _, year, month, dayOfMonth ->
                    val localDateTime = LocalDateTime
                        .of(year, month, dayOfMonth, 0, 0)
                    setTextFromDate(dateField as TextView, localDateTime)
                }

                this@AddRatingBottomSheet.fragmentManager?.let { show(it, "") }
            }
    }

    private fun insertRating(it: View) {
        if(addRatingModel.rate <0f) addRatingModel.rate= 0f
        onRatingSet?.invoke(position, addRatingModel)
        Logger.i(addRatingModel.toString())
    }

}