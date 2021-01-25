package com.ouzkse.itemtouchhelperpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import com.ouzkse.itemtouchhelperpractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TouchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val itemTouchHelper = TouchHelper()
        itemTouchHelper.itemTouchHelper.attachToRecyclerView(binding.recyclerView)
        adapter = TouchAdapter(itemTouchHelper)
        binding.recyclerView.adapter = adapter
        adapter.submitList(getDummyItems())
    }

    private fun getDummyItems(): List<ItemModel> {
        return arrayListOf<ItemModel>().apply {
            for (i in 0 .. 10) {
                add(ItemModel(i, "This is item $i"))
            }
        }.toList()
    }
}