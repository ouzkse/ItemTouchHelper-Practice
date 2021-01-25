package com.ouzkse.itemtouchhelperpractice

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ouzkse.itemtouchhelperpractice.databinding.RecyclerViewItemLayoutBinding

class TouchAdapter(private val itemTouchHelper: TouchHelper): RecyclerView.Adapter<TouchAdapter.ItemModelViewHolder>() {

    var items = mutableListOf<ItemModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemModelViewHolder {
        return ItemModelViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ItemModelViewHolder, position: Int) {
        holder.bind(items[position], itemTouchHelper)
    }

    override fun getItemCount(): Int = items.size

    fun submitList(list: List<ItemModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun moveItem(from: Int, to: Int) {
        val item = items[from]
        items.remove(item)
        items.add(to, item)
    }

    class ItemModelViewHolder(private val binding: RecyclerViewItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ClickableViewAccessibility")
        fun bind(item: ItemModel, itemTouchHelper: TouchHelper) {
            binding.item = item

            binding.imageDrag.setOnTouchListener { view, motionEvent ->
                if (motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
                    itemTouchHelper.itemTouchHelper.startDrag(this)
                }
                return@setOnTouchListener true
            }

            binding.layout.setOnTouchListener { view, motionEvent ->
                if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                    view.tag = true
                    Log.d("OUZ", "Tag true landı - ActionDown")
                } else if (view.isPressed && view.tag as Boolean) {
                    Log.d("OUZ", "isPressed ve tag true")
                    val eventDuration = motionEvent.eventTime - motionEvent.downTime
                    Log.d("OUZ", "Duration $eventDuration")
                    Log.d("OUZ", "eventTime: ${motionEvent.eventTime} / downTime: ${motionEvent.downTime}")

                    if (eventDuration > 200) {
                        view.tag = false
                        itemTouchHelper.itemTouchHelper.startDrag(this)
                    }
                }
                return@setOnTouchListener false
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ItemModelViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecyclerViewItemLayoutBinding.inflate(layoutInflater, parent, false)
                return ItemModelViewHolder(binding)
            }
        }
    }
    class ItemModelDiffCallback : DiffUtil.ItemCallback<ItemModel>() {
        override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel) = (oldItem.id == newItem.id)
        override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel) = (oldItem == newItem)
    }
}