package com.bignerdranch.android.criminalintent


import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import androidx.fragment.app.DialogFragment
import java.util.*

/**
 * Chapter 13 challenge
 */

private const val ARG_TIME = "time"
class TimePickerFragment : DialogFragment(){


        interface Callbacks{
            fun onTimelected(time: String)
        }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val timeListener = TimePickerDialog.OnTimeSetListener {
                    view, hourOfDay, minute ->
                val resultTime: String = "$hourOfDay:$minute"

                targetFragment?.let{fragment ->
                    (fragment as Callbacks).onTimelected(resultTime)

                }
            }
            val time = arguments?.getSerializable(ARG_TIME) as String
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)


            return TimePickerDialog(
                requireContext(),
                timeListener,
                hour,
                minute,
                is24HourFormat(activity)

            )
        }

        companion object{
            fun newInstance(time: String) : TimePickerFragment{
                val args = Bundle().apply{
                    putSerializable(ARG_TIME, time)
                }

                return TimePickerFragment().apply {
                    arguments = args
                }
            }
        }

}