package com.pdarcas.myapponthemove.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pdarcas.myapponthemove.data.entities.RecordModel
import com.pdarcas.myapponthemove.R

class RecordListAdapter (private val list: List<RecordModel>, val click:(RecordModel)->Unit): RecyclerView.Adapter<RecordListAdapter.RecordViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecordListAdapter.RecordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_record,parent,false)
        return RecordViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecordListAdapter.RecordViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount()= list.size

    inner class RecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(record:RecordModel){
            itemView.findViewById<TextView>(R.id.recordTextView).text=record.name
            itemView.setOnClickListener {
                click.invoke(record)
            }
        }

    }
}