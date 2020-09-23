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
import kotlinx.android.synthetic.main.activity_main.view.imageView
import kotlinx.android.synthetic.main.item_data_cell.view.*
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
        //holder.titletextView.text = task.title
       // holder.pricetextView.text =task.price
        //holder.timetextView.text = task.createdAt

            //SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.JAPANESE).format(task.createdAt)

    }

    //fun addAll(items:List<ItemData>){
      //  this.taskList?.addAll(items)
      //  notifyDataSetChanged()
    //}

    //override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TaskViewHolder {
        //val v = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false)
        //return TaskViewHolder(v)
    //}

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.imageView
        val title: TextView = view.titleText
        val price: TextView = view.priceText
        val time:TextView = view.timetextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        TODO("Not yet implemented")
    }

}