package com.lifeistech.l4s.challengeproduct

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.view.*
import java.text.SimpleDateFormat
import java.util.*


class TaskAdapter(
    private val context: Context,
    private var taskList: OrderedRealmCollection<Task>?,
    private val autoUpdate: Boolean
) :
    RealmRecyclerViewAdapter<Task, TaskAdapter.TaskViewHolder>(taskList, autoUpdate) {

    override fun getItemCount(): Int = taskList?.size ?: 0

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task: Task = taskList?.get(position) ?: return

        holder.imageView.setImageResource(task.imageId)
        //holder.contentTextView.text = task.content
       // holder.dateTextView.text =
            SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.JAPANESE).format(task.createdAt)

    }

    //override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TaskViewHolder {
        //val v = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false)
        //return TaskViewHolder(v)
    //}

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.imageView
        //val contentTextView: TextView = view.contentTextView
        //val dateTextView: TextView = view.dateTextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        TODO("Not yet implemented")
    }

}