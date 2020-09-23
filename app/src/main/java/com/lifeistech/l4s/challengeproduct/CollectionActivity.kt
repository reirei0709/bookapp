package com.lifeistech.l4s.challengeproduct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_collection.*
import java.net.URI.create
import java.util.*
import java.util.Collections.addAll

//val ItemData:List<ItemData> = mutableListOf(

//)

class CollectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)

        val realm: Realm by lazy {
            Realm.getDefaultInstance()
        }

        fun readAll(): OrderedRealmCollection<Task>? {

                return realm.where(Task::class.java).findAll().sort("createdAt", Sort.ASCENDING)


        }

        fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            val taskList = readAll()

            //// タスクリストが空だったときにダミーデータを生成する
            //if (taskList.isEmpty()) {
              //  createDummyData()
            //}

            val adapter = TaskAdapter(this, taskList, true)

            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter

        }

        fun onDestroy() {
            super.onDestroy()
            realm.close()
        }

        //fun createDummyData() {
           // for (i in 0..10) {
               // create(R.drawable.ic_launcher_background, "やること $i")
            //}
        //}

        fun create(imageId: Int, content: String) {
            realm.executeTransaction {
                val task = it.createObject(Task::class.java, UUID.randomUUID().toString())
                task.imageId = imageId
                task.title= content
            }
        }









        //val adapter = TaskAdapter(this)
        //recyclerView.layoutManager = LinearLayoutManager(this)
        //recyclerView.adapter = adapter

       //adapter.addAll(ItemData)

    }
}