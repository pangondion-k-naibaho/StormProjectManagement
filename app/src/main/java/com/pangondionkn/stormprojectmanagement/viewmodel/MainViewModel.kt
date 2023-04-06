package com.pangondionkn.stormprojectmanagement.viewmodel

import android.app.Application
import android.os.Handler
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pangondionkn.stormprojectmanagement.model.data_class.Project
import com.pangondionkn.stormprojectmanagement.model.data_local.ProjectDao
import com.pangondionkn.stormprojectmanagement.model.data_local.ProjectDatabase

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val TAG = MainViewModel::class.java.simpleName

    private var _isListEmpty = MutableLiveData<Boolean>()
    val isListEmpty: LiveData<Boolean> = _isListEmpty

    private var _listProject = MutableLiveData<List<Project>>()
    var listProject : LiveData<List<Project>> = _listProject

    private var _isSuccessDeleteData = MutableLiveData<Boolean>()
    val isSuccessDeleteData: LiveData<Boolean> = _isSuccessDeleteData

    private var projectDao: ProjectDao?= null
    private var projectDB: ProjectDatabase?= ProjectDatabase.getDatabase(application)

    init {
        projectDao = projectDB?.projectDao()
    }

    fun getListProduct(){
        listProject = projectDao?.getAllProject()!!
        _isListEmpty.value = false
    }

    fun getListProjectCondition(){
        if (listProject.value == null){
            _isListEmpty.value = true
        }
    }

    fun deleteProject(projectName: String){
//        Handler().postDelayed(
//            {
//                _isLoading.value = false
//            },3000
//        )
        projectDao?.deleteProject(projectName)
//        Handler().postDelayed(
//            {
//                _isLoading.value = true
//            },1000
//        )
        _isSuccessDeleteData.value = true
    }
}