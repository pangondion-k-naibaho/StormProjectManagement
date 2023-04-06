package com.pangondionkn.stormprojectmanagement.model.data_local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pangondionkn.stormprojectmanagement.model.data_class.Project

@Dao
interface ProjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProject(project: Project)

    @Query("SELECT * FROM Project")
    fun getAllProject(): LiveData<List<Project>>

    @Query("UPDATE Project SET projectName = :projectName, projectDeadline = :projectDeadline, userName = :userName, approval = :approval, projectBudget = :projectBudget, startDate = :startDate WHERE projectName = :previousProjectName")
    fun updateProject(projectName: String, projectDeadline: String, userName: String, approval: Boolean, projectBudget: Long, startDate: String, previousProjectName: String)

    @Query("DELETE FROM Project WHERE projectName = :projectName")
    fun deleteProject(projectName: String)
}