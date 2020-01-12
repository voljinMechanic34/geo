package com.example.geo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_selector.*

class SelectorActivity : AppCompatActivity() {
    lateinit var adapter: ServiceAdapter
    var listSelectedItems : ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selector)

        val layout = LinearLayoutManager(this)
        val adapter = ServiceAdapter()

        recyclerView.layoutManager  = layout
        recyclerView.adapter = adapter


        val intent = intent
        val list = intent.getStringArrayListExtra("test")
        Log.d("Result",list.get(0))
        if (list.size > 0){
            adapter.setListItem(list)
            adapter.notifyDataSetChanged()
        }
        button_service.setOnClickListener{
           listSelectedItems =  adapter.getSelectItems()


            val intent = Intent(this,MapsActivity::class.java)
            intent.putStringArrayListExtra("SelectedItems", listSelectedItems )
            startActivity(intent)
        }

    }
}
