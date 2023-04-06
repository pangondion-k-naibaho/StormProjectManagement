package com.pangondionkn.stormprojectmanagement.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pangondionkn.stormprojectmanagement.R
import com.pangondionkn.stormprojectmanagement.databinding.ActivityMainBinding
import com.pangondionkn.stormprojectmanagement.model.Utils.DUMMY_DATA.Companion.getDummyListProject
import com.pangondionkn.stormprojectmanagement.model.data_class.Project
import com.pangondionkn.stormprojectmanagement.view.adapter.ItemProjectAdapter
import com.pangondionkn.stormprojectmanagement.view.advanced_ui.PopUpDialogListener
import com.pangondionkn.stormprojectmanagement.view.advanced_ui.showPopupDialog
import com.pangondionkn.stormprojectmanagement.view.advanced_ui.showPopupQuestionDialog
import com.pangondionkn.stormprojectmanagement.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG = MainActivity::class.java.simpleName
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        mainViewModel.getListProjectCondition()
        mainViewModel.isListEmpty.observe(this, {
            setUpEmptyDisplay(it)
        })
//        setUpRvProject()
        mainViewModel.getListProduct()
        mainViewModel.listProject.observe(this, {listProjects ->
            setUpRvProject(listProjects)
        })

        setUpAddProject()
    }

    private fun setUpEmptyDisplay(isListEmpty: Boolean){
        Log.d(TAG, "isListEmpty: ${isListEmpty}")
        if(isListEmpty){
            binding.clEmptyList.visibility = View.VISIBLE
            binding.rvItemProject.visibility = View.GONE
        }else{
            binding.clEmptyList.visibility = View.GONE
            binding.rvItemProject.visibility = View.VISIBLE
        }
    }

    private fun setUpRvProject(listProjects: List<Project>){
        binding.rvItemProject.setHasFixedSize(true)
        binding.rvItemProject.layoutManager = LinearLayoutManager(this)
        binding.rvItemProject.isNestedScrollingEnabled = false

        val adapter = ItemProjectAdapter(listProjects, object: ItemProjectAdapter.ItemClickListener{
            override fun onItemEditClick(item: Project) {
                Log.d(TAG, "Edited item: ${item}")
                startActivity(
                    AddEditActivity.newIntent(this@MainActivity, isAdd = false, deliveredProject = item)
                )
            }

            override fun onItemDeleteClick(item: Project) {
                Log.d(TAG, "Deleted item: ${item}")
                showPopupQuestionDialog(
                    drawable = ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_question_white)!!,
                    background = ContextCompat.getDrawable(this@MainActivity, R.drawable.bg_circle_blue)!!,
                    textDesc = getString(R.string.popup_question_delete_desc),
                    listener = object: PopUpDialogListener{
                        override fun onClickYes() {
                            mainViewModel.deleteProject(item.projectName)
                            mainViewModel.isSuccessDeleteData.observe(this@MainActivity, {
                                if(it){
                                    showPopupDialog(
                                        drawable = ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_check_white)!!,
                                        background = ContextCompat.getDrawable(this@MainActivity, R.drawable.bg_circle_green)!!,
                                        textDesc = getString(R.string.popup_success_delete_desc),
                                        listener = object: PopUpDialogListener{
                                            override fun onClickListener() {
                                                closeOptionsMenu()
                                            }
                                        }
                                    )
                                }else{
                                    showPopupDialog(
                                        drawable = ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_close_white)!!,
                                        background = ContextCompat.getDrawable(this@MainActivity, R.drawable.bg_circle_red)!!,
                                        textDesc = getString(R.string.popup_not_success_delete_desc),
                                        listener = object: PopUpDialogListener{
                                            override fun onClickListener() {
                                                closeOptionsMenu()
                                            }
                                        }
                                    )
                                }
                            })
                        }

                        override fun onClickCancel() {
                            closeOptionsMenu()
                        }
                    }
                )
            }

        })

        binding.rvItemProject.adapter = adapter
    }

    private fun setUpAddProject(){
        binding.btnAddProject.setOnClickListener {
            startActivity(
                AddEditActivity.newIntent(this@MainActivity, true)
            )
        }
    }
}