package com.example.dominictools.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dominictools.R
import com.example.dominictools.data.entities.relations.ToolAndLoans

class ToolAndLoanAdapter(private val listener: ToolClickListener) : ListAdapter<ToolAndLoans, ToolAndLoanAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ToolAndLoans>() {
            override fun areItemsTheSame(oldData: ToolAndLoans, newData: ToolAndLoans): Boolean {
                return oldData.tool.name == newData.tool.name
            }

            override fun areContentsTheSame(oldData: ToolAndLoans, newData: ToolAndLoans): Boolean {
                return oldData == newData
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToolAndLoanAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_tool, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToolAndLoanAdapter.MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var toolName: TextView = itemView.findViewById(R.id.tvToolName)
        var toolAvailable: TextView = itemView.findViewById(R.id.tvToolAvailable)
        var toolBorrowed: TextView = itemView.findViewById(R.id.tvToolBorrowed)

        fun bind(toolAndLoans: ToolAndLoans){
            toolName.text = toolAndLoans.tool.name
            val borrowed = toolAndLoans.loans.sumOf { it.count }
            val available = "${toolAndLoans.tool.count - borrowed} Available"
            val borrowedText = "$borrowed Borrowed"
            toolAvailable.text = available
            toolBorrowed.text = borrowedText
            itemView.setOnClickListener { listener.onToolClicked(adapterPosition, toolAndLoans) }
        }
    }

    interface ToolClickListener{
        fun onToolClicked(position: Int, toolAndLoans: ToolAndLoans)
    }
}