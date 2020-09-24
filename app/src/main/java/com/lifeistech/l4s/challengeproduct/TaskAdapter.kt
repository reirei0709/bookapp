package com.lifeistech.l4s.challengeproduct

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_main.view.imageView
import kotlinx.android.synthetic.main.item_data_cell.view.*
import java.text.SimpleDateFormat
import java.util.*


class TaskAdapter(
    private val context: Context,
    private var taskList: OrderedRealmCollection<Task>?,
    private var listener: OnItemClickListener,
    private val autoUpdate: Boolean
) :
    RealmRecyclerViewAdapter<Task, TaskAdapter.TaskViewHolder>(taskList, autoUpdate) {

    override fun getItemCount(): Int = taskList?.size ?: 0

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task: Task = taskList?.get(position) ?: return

        holder.container.setOnClickListener{
            listener.onItemClick(task)
        }

        //holder.imageView.setImageResource(task.imageId)
        holder.titleTextView.text = task.title
        holder.authorTextView.text = task.author
        holder.priceTextView.text = task.price
        holder.timeTextView.text = task.createdAt.toString()

        //holder.dateTextView.text =
            //SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.JAPANESE).format(task.createdAt)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TaskViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_data_cell, viewGroup, false)
        return TaskViewHolder(v)
    }

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //val imageView: ImageView = view.imageView
        val container:ConstraintLayout = view.container
        val titleTextView: TextView = view.titletextView
        val authorTextView: TextView = view.authortextView
        val priceTextView:TextView = view.pricetextView
        val timeTextView:TextView = view.timetextView
    }
    interface OnItemClickListener {
        fun onItemClick(item: Task)
    }

}