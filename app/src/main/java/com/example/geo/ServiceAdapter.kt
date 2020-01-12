package com.example.geo

import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_service.view.*
import java.util.*
import kotlin.collections.ArrayList

class ServiceAdapter : RecyclerView.Adapter<ServiceAdapter.ViewHolder>() {
    var itemList: List<String> = Collections.emptyList()
    var selectedItems: ArrayList<String> = ArrayList()

    fun setListItem(listItemS: List<String>) {
        itemList = listItemS
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_service,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun getSelectItems(): ArrayList<String> {
        return selectedItems
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemService = itemList.get(position)
        holder.textService.text = itemService

        if (itemService.equals("a")){
            holder.textService.setTextColor(Color.RED)
        }
        if (itemService.equals("b")){
            holder.textService.setTextColor(Color.CYAN)
        }
        if (itemService.equals("c")){
            holder.textService.setTextColor(Color.YELLOW)
        }

    }

  inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val selectState : CheckBox = itemView.checkbox_id

        val textService : TextView = itemView.textView_service

        init {
            selectState.setOnClickListener{
                if (selectState.isChecked) {
                    Toast.makeText(itemView.context, "select check is " + selectState.isChecked, Toast.LENGTH_SHORT)
                        .show()
                    selectedItems.add(itemList.get(adapterPosition))
                    Log.d("List",selectedItems.size.toString())
                } else {


                    selectedItems.remove(textService.text.toString())
                    Log.d("List",selectedItems.size.toString())
                }
            }
        }

    }
}