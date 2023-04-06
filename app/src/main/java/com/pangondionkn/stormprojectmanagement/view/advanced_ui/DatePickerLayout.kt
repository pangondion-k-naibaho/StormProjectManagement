package com.pangondionkn.stormprojectmanagement.view.advanced_ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.pangondionkn.stormprojectmanagement.R
import com.pangondionkn.stormprojectmanagement.databinding.LayoutDatepickerBinding

class DatePickerLayout: ConstraintLayout {
    private lateinit var mContext: Context
    private lateinit var binding: LayoutDatepickerBinding
    private var datePickerListener: DatePickerListener?= null

    constructor(context: Context):super(context){
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet):super(context, attrs){
        init(context, attrs)
    }

    private fun init(context: Context, attributeSet: AttributeSet?){
        mContext = context

        binding = LayoutDatepickerBinding.bind(
            LayoutInflater.from(context)
                .inflate(R.layout.layout_datepicker, this, true)
        )

        binding.icDatePicker.setOnClickListener {
            datePickerListener?.onClickCalendar()
        }
    }

    fun setText(inputText:String){
        binding.etDatePicker.setText(inputText)
    }

    fun getText(): String{
        return binding.etDatePicker.text.toString()
    }

    fun setListener(listener: DatePickerListener){
        datePickerListener = listener
    }

    interface DatePickerListener{
        fun onClickCalendar(){}
    }
}