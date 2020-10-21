package com.smart.resources.schools_app.features.dateTimePickers

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

class DatePickerFragment : DialogFragment() {
    var onDateSet: ((date:LocalDate)->Unit)?= null


    companion object Factory{
        fun newInstance()= DatePickerFragment()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        with(LocalDateTime.now()) {
            DatePickerDialog(
                requireContext(),
                {_,year, month, dayOfMonth ->
                   val date= LocalDate.of(year, month+1, dayOfMonth)
                    onDateSet?.invoke(date)
                },
                year,
                monthValue-1,
                dayOfMonth
            )
        }
}