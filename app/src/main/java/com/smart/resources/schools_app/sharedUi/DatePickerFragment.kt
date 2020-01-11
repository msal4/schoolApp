package com.smart.resources.schools_app.sharedUi

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import org.threeten.bp.LocalDateTime

class DatePickerFragment : DialogFragment() {
    var onDateSet: ((View, Int, Int, Int)->Unit)?= null

    companion object Factory{

        fun newInstance()= DatePickerFragment()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        with(LocalDateTime.now()) {
            DatePickerDialog(
                context!!,
                onDateSet,
                year,
                monthValue,
                dayOfMonth
            )
        }
}