package com.pangondionkn.stormprojectmanagement.view.activity

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import com.pangondionkn.stormprojectmanagement.R
import com.pangondionkn.stormprojectmanagement.databinding.ActivityAddEditBinding
import com.pangondionkn.stormprojectmanagement.model.data_class.Project
import com.pangondionkn.stormprojectmanagement.view.advanced_ui.DatePickerLayout
import com.pangondionkn.stormprojectmanagement.view.advanced_ui.PopUpDialogListener
import com.pangondionkn.stormprojectmanagement.view.advanced_ui.showPopupDialog
import com.pangondionkn.stormprojectmanagement.viewmodel.AddEditViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEditBinding
    private val TAG = AddEditActivity::class.java.simpleName
    private lateinit var addEditViewModel: AddEditViewModel
//    private lateinit var deliveredProject: Project
    private var prevProjectName = ""

    companion object{
        const val EXTRA_ADD = "EXTRA_ADD"
        const val EXTRA_EDIT = "EXTRA_EDIT"
        const val EXTRA_PROJECT = "EXTRA_PROJECT"

        fun newIntent(context: Context, isAdd: Boolean, deliveredProject:Project?= null) = Intent(context, AddEditActivity::class.java)
            .putExtra(EXTRA_ADD, isAdd)
            .putExtra(EXTRA_PROJECT, deliveredProject)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val isAdd = intent.extras?.getBoolean(EXTRA_ADD)
//        deliveredProject = intent.extras?.getParcelable(EXTRA_PROJECT)!!

        addEditViewModel = ViewModelProvider(this).get(AddEditViewModel::class.java)

        addEditViewModel.isLoading.observe(this, {
            setUpLoading(it)
        })

        when(isAdd!!){
            true->{
                setupPage_e1()
            }
            false->{
                setUpPage_e2()
            }
        }
    }

    private fun setupPage_e1(){
        binding.tvTitleAddEdit.text = getString(R.string.tv_addTitle)
        binding.btnAddorSaveEdit.text = getString(R.string.btnTxtAddEdit_Add)

        binding.dplStartDate.apply {
            setListener(object: DatePickerLayout.DatePickerListener{
                override fun onClickCalendar() {
                    val myCalendar = Calendar.getInstance()
                    val dpdListener = DatePickerDialog.OnDateSetListener{_, year, monthOfYear, dayOfMonth ->
                        myCalendar.set(Calendar.YEAR, year)
                        myCalendar.set(Calendar.MONTH, monthOfYear)
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
                        setText(sdf.format(myCalendar.time))
                    }
                    DatePickerDialog(context, dpdListener,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            })
        }

        binding.dplDeadline.apply {
            setListener(object: DatePickerLayout.DatePickerListener{
                override fun onClickCalendar() {
                    val myCalendar = Calendar.getInstance()
                    val dpdListener = DatePickerDialog.OnDateSetListener{_, year, monthOfYear, dayOfMonth ->
                        myCalendar.set(Calendar.YEAR, year)
                        myCalendar.set(Calendar.MONTH, monthOfYear)
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
                        setText(sdf.format(myCalendar.time))
                    }
                    DatePickerDialog(context, dpdListener,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            })
        }

        binding.btnAddorSaveEdit.setOnClickListener {
            val projectNeedtoAdded = Project(
                projectName = binding.etProjectName.text.toString(),
                projectDeadline = binding.dplDeadline.getText(),
                userName = binding.etUsername.text.toString(),
                approval = binding.cbApproval.isChecked,
                projectBudget = binding.etBudgetProject.text.toString().toLong(),
                startDate = binding.dplStartDate.getText()
            )
            Log.d(TAG, "Project need to be added: ${projectNeedtoAdded}")
            addEditViewModel.addProject(projectNeedtoAdded)
            addEditViewModel.isSuccessAddData.observe(this, {
                if(it){
                    showPopupDialog(
                        drawable = AppCompatResources.getDrawable(this@AddEditActivity, R.drawable.ic_check_white)!!,
                        background = AppCompatResources.getDrawable(this@AddEditActivity, R.drawable.bg_circle_green)!!,
                        textDesc = getString(R.string.popup_success_add_desc),
                        listener = object: PopUpDialogListener{
                            override fun onClickListener() {
                                finish()
                            }
                        }
                    )
                }else {
                    showPopupDialog(
                        drawable = AppCompatResources.getDrawable(this@AddEditActivity, R.drawable.ic_close_white)!!,
                        background = AppCompatResources.getDrawable(this@AddEditActivity, R.drawable.bg_circle_red)!!,
                        textDesc = getString(R.string.popup_not_success_add_desc),
                        listener = object: PopUpDialogListener{
                            override fun onClickListener() {
                                closeOptionsMenu()
                            }
                        }
                    )
                }
            })
        }
    }

    private fun setUpPage_e2(){
        val deliveredProject = intent.getParcelableExtra<Project>(EXTRA_PROJECT)

        binding.tvTitleAddEdit.text = getString(R.string.tv_editTitle)
        binding.btnAddorSaveEdit.text = getString(R.string.btnTxtAddEdit_Edit)

        prevProjectName = deliveredProject!!.projectName

        var userName: CharSequence = deliveredProject!!.userName
        var budgetProject : CharSequence = deliveredProject!!.projectBudget.toString()
        var projectName: CharSequence = deliveredProject!!.projectName

        binding.etUsername.setText(userName)
        binding.etBudgetProject.setText(budgetProject)
        binding.etProjectName.setText(projectName)

        when(deliveredProject.approval){
            true ->{
                binding.cbApproval.isChecked = true
            }
            false ->{
                binding.cbApproval.isChecked = false
            }
        }

        binding.dplStartDate.apply {
            setText(deliveredProject!!.startDate)
            setListener(object: DatePickerLayout.DatePickerListener{
                override fun onClickCalendar() {
                    val myCalendar = Calendar.getInstance()
                    val dpdListener = DatePickerDialog.OnDateSetListener{_, year, monthOfYear, dayOfMonth ->
                        myCalendar.set(Calendar.YEAR, year)
                        myCalendar.set(Calendar.MONTH, monthOfYear)
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
                        setText(sdf.format(myCalendar.time))
                    }
                    DatePickerDialog(context, dpdListener,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            })
        }

        binding.dplDeadline.apply {
            setText(deliveredProject!!.projectDeadline)
            setListener(object: DatePickerLayout.DatePickerListener{
                override fun onClickCalendar() {
                    val myCalendar = Calendar.getInstance()
                    val dpdListener = DatePickerDialog.OnDateSetListener{_, year, monthOfYear, dayOfMonth ->
                        myCalendar.set(Calendar.YEAR, year)
                        myCalendar.set(Calendar.MONTH, monthOfYear)
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
                        setText(sdf.format(myCalendar.time))
                    }
                    DatePickerDialog(context, dpdListener,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            })
        }

        binding.btnAddorSaveEdit.setOnClickListener {
            val projectNeedtoEdited = Project(
                projectName = binding.etProjectName.text.toString(),
                projectDeadline = binding.dplDeadline.getText(),
                userName = binding.etUsername.text.toString(),
                approval = binding.cbApproval.isChecked,
                projectBudget = binding.etBudgetProject.text.toString().toLong(),
                startDate = binding.dplStartDate.getText()
            )
            Log.d(TAG, "Project need to be edited: ${projectNeedtoEdited}")
            addEditViewModel.editProject(projectNeedtoEdited, prevProjectName)
            addEditViewModel.isSuccessEditData.observe(this, {
                if(it){
                    showPopupDialog(
                        drawable = AppCompatResources.getDrawable(this@AddEditActivity, R.drawable.ic_check_white)!!,
                        background = AppCompatResources.getDrawable(this@AddEditActivity, R.drawable.bg_circle_green)!!,
                        textDesc = getString(R.string.popup_success_edit_desc),
                        listener = object: PopUpDialogListener{
                            override fun onClickListener() {
                                finish()
                            }
                        }
                    )
                }else {
                    showPopupDialog(
                        drawable = AppCompatResources.getDrawable(this@AddEditActivity, R.drawable.ic_close_white)!!,
                        background = AppCompatResources.getDrawable(this@AddEditActivity, R.drawable.bg_circle_red)!!,
                        textDesc = getString(R.string.popup_not_success_edit_desc),
                        listener = object: PopUpDialogListener{
                            override fun onClickListener() {
                                closeOptionsMenu()
                            }
                        }
                    )
                }
            })
        }
    }

    private fun setUpLoading(isLoading: Boolean){
        if(isLoading){
            binding.pbAddEdit.visibility = View.VISIBLE
        }else binding.pbAddEdit.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}