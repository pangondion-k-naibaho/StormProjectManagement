package com.pangondionkn.stormprojectmanagement.model.data_class

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import com.google.gson.annotations.SerializedName

@Parcelize
@Entity(tableName = "Project")
data class Project(
    @field:SerializedName("Name")
    @PrimaryKey(autoGenerate = false)
    var projectName: String = "",

    @field:SerializedName("Deadline")
    var projectDeadline: String = "",

    @field:SerializedName("Username")
    var userName: String = "",

    @field:SerializedName("Approval")
    var approval: Boolean = false,

    @field:SerializedName("Budget")
    var projectBudget: Long = 0L,

    @field:SerializedName("Start Date")
    var startDate: String = ""
):Parcelable