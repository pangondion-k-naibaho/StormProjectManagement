package com.pangondionkn.stormprojectmanagement.model.data_local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pangondionkn.stormprojectmanagement.model.data_class.Project

@Database(
    entities = [Project::class],
    version = 1
)

abstract class ProjectDatabase: RoomDatabase() {
    companion object{
        private var INSTANCE: ProjectDatabase?= null

        fun getDatabase(context: Context): ProjectDatabase?{
            if(INSTANCE == null){
                synchronized(ProjectDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ProjectDatabase::class.java,
                        "Project"
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun projectDao(): ProjectDao
}