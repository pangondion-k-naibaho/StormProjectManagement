package com.pangondionkn.stormprojectmanagement.view.advanced_ui

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.Window
import com.pangondionkn.stormprojectmanagement.R
import com.pangondionkn.stormprojectmanagement.databinding.LayoutPopupBinaryBinding
import com.pangondionkn.stormprojectmanagement.databinding.LayoutPopupspmBinding


interface PopUpDialogListener{
    fun onClickListener(){}
    fun onClickYes(){}
    fun onClickCancel(){}
}

fun Activity.showPopupDialog(
    drawable: Drawable,
    background: Drawable,
    textDesc: String,
    listener: PopUpDialogListener
){
    val dialog = Dialog(this)
    val binding = LayoutPopupspmBinding.bind(layoutInflater.inflate(R.layout.layout_popupspm, null))
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setContentView(binding.root)
    dialog.setCancelable(listener == null)
    binding.apply {
        ivIcPopup.setImageDrawable(drawable)
        ivIcPopup.setBackgroundDrawable(background)
        tvDescPopUp.text = textDesc
        btnPopup.setOnClickListener {
            listener?.onClickListener()
            dialog.dismiss()
        }
        if(!isFinishing) dialog.show()
    }
}

fun Activity.showPopupQuestionDialog(
    drawable: Drawable,
    background: Drawable,
    textDesc: String,
    listener: PopUpDialogListener
){
    val dialog = Dialog(this)
    val binding = LayoutPopupBinaryBinding.bind(layoutInflater.inflate(R.layout.layout_popup_binary, null))
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setContentView(binding.root)
    dialog.setCancelable(listener == null)
    binding.apply {
        ivIcPopup.setImageDrawable(drawable)
        ivIcPopup.setBackgroundDrawable(background)
        tvDescPopUp.text = textDesc
        btnYes.setOnClickListener {
            listener.onClickYes()
            dialog.dismiss()
        }
        btnCancel.setOnClickListener {
            listener.onClickCancel()
            dialog.dismiss()
        }
        if(!isFinishing) dialog.show()
    }
}