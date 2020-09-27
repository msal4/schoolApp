package com.smart.resources.schools_app.sharedUi.fragment

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import org.threeten.bp.LocalTime

class TimePickerFragment : DialogFragment() {
    var onTimeSet: ((time: LocalTime) -> Unit)? = null


    companion object Factory {
        fun newInstance() = TimePickerFragment()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        with(LocalTime.now()) {
            TimePickerDialog(
                requireContext(),
                { _, hour, minute ->
                    val time = LocalTime.of(hour, minute)
                    onTimeSet?.invoke(time)
                },
                hour,
                minute,
                false
            )
        }
}