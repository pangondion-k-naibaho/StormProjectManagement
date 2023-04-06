package com.pangondionkn.stormprojectmanagement.model

import com.pangondionkn.stormprojectmanagement.model.data_class.Project

class Utils {
    interface DUMMY_DATA{
        companion object{
            fun getDummyListProject(): List<Project> = listOf(
                Project(
                    projectName = "Pembangunan Jembatan Kenangan",
                    projectDeadline = "November 2029",
                    userName = "ZephyrSensei",
                    approval = false,
                    projectBudget = 5000000000,
                    startDate = "April 2023"
                ),
                Project(
                    projectName = "Pembangunan Rider System",
                    projectDeadline = "November 2029",
                    userName = "Takumi Inui",
                    approval = true,
                    projectBudget = 5000000000,
                    startDate = "April 2023"
                ),
                Project(
                    projectName = "Pembangunan Rider System",
                    projectDeadline = "November 2029",
                    userName = "Takumi Inui",
                    approval = true,
                    projectBudget = 5000000000,
                    startDate = "April 2023"
                )
            )
        }
    }
}