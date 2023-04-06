package com.pangondionkn.stormprojectmanagement.viewmodel

import android.app.Application
import android.os.Handler
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pangondionkn.stormprojectmanagement.model.data_class.Project
import com.pangondionkn.stormprojectmanagement.model.data_local.ProjectDao
import com.pangondionkn.stormprojectmanagement.model.data_local.ProjectDatabase


class AddEditViewModel(application: Application): AndroidViewModel(application) {
    private val TAG = AddEditViewModel::class.java.simpleName
    private var projectDao: ProjectDao?= null
    private var projectDB: ProjectDatabase?= ProjectDatabase.getDatabase(application)

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isSuccessAddData = MutableLiveData<Boolean>()
    val isSuccessAddData: LiveData<Boolean> = _isSuccessAddData

    private var _isSuccessEditData = MutableLiveData<Boolean>()
    val isSuccessEditData: LiveData<Boolean> = _isSuccessEditData

    init {
        projectDao = projectDB?.projectDao()
    }

    fun addProject(projectAdded: Project){
        Handler().postDelayed(
            {
                _isLoading.value = false
            },3000
        )
        projectDao?.addProject(projectAdded)
        Handler().postDelayed(
            {
                _isLoading.value = true
            },1000
        )
        _isSuccessAddData.value = true
    }

    fun editProject(projectEdited: Project, previousProjectName: String){
        Handler().postDelayed(
            {
                _isLoading.value = false
            },3000
        )
        projectDao?.updateProject(
            projectEdited.projectName,
            projectEdited.projectDeadline,
            projectEdited.userName,
            projectEdited.approval,
            projectEdited.projectBudget,
            projectEdited.startDate,
            previousProjectName
        )
        Handler().postDelayed(
            {
                _isLoading.value = true
            },1000
        )
        _isSuccessEditData.value = true
    }
}