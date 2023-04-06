package com.pangondionkn.stormprojectmanagement.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pangondionkn.stormprojectmanagement.R
import com.pangondionkn.stormprojectmanagement.databinding.LayoutItemProjectBinding
import com.pangondionkn.stormprojectmanagement.model.data_class.Project

class ItemProjectAdapter(
    var data: List<Project>?= null,
    private val listener: ItemClickListener
): RecyclerView.Adapter<ItemProjectAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(item:Project, listener: ItemClickListener, position: Int){
            val binding = LayoutItemProjectBinding.bind(itemView)

            binding.tvProjectName.text = item.projectName
            binding.tvProjectDate.text = "${item.startDate} - ${item.projectDeadline}"
            binding.tvUsername.text = item.userName
            binding.tvBudgetproject.text = "Rp ${item.projectBudget}"
            when(item.approval){
                true ->{
                    binding.tvApproval.text = itemView.context.getString(R.string.tv_Approved)
                    binding.ivIcApproval.setImageDrawable(itemView.resources.getDrawable(R.drawable.ic_check_white))
                    binding.ivIcApproval.background = itemView.resources.getDrawable(R.drawable.bg_circle_green)
                }
                false ->{
                    binding.tvApproval.text = itemView.context.getString(R.string.tv_NotApproved)
                    binding.ivIcApproval.setImageDrawable(itemView.resources.getDrawable(R.drawable.ic_close_white))
                    binding.ivIcApproval.background = itemView.resources.getDrawable(R.drawable.bg_circle_red)
                }
            }

            binding.btnDeleteProject.setOnClickListener {
                listener.onItemDeleteClick(item)
            }

            binding.btnEditProject.setOnClickListener {
                listener.onItemEditClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_item_project, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data!![position], listener, position)
    }

    override fun getItemCount(): Int = data!!.size

    interface ItemClickListener{
        fun onItemEditClick(item: Project)
        fun onItemDeleteClick(item: Project)
    }

}