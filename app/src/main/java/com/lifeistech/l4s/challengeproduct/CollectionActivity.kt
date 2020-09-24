package com.lifeistech.l4s.challengeproduct

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_collection.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class CollectionActivity : AppCompatActivity() {

    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    fun readAll(): RealmResults<Task> {
        return realm.where(Task::class.java).findAll().sort("createdAt", Sort.ASCENDING)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)


        val taskList = readAll()

        // タスクリストが空だったときにダミーデータを生成する
        if (taskList.isEmpty()) {
            createDummyData()
        }

        //val adapter = TaskAdapter(this, taskList, true)

        val adapter =
            TaskAdapter(this, taskList, object : TaskAdapter.OnItemClickListener {
                override fun onItemClick(item: Task) {
                    // クリック時の処理
                    Toast.makeText(applicationContext, item.content + "を削除しました", Toast.LENGTH_SHORT).show()
                    delete(item.id)
                }
            }, true)

        if(recyclerView == null) Log.d("recyclerView", "null")
        else{
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = GridLayoutManager(this, 3)
            recyclerView.adapter = adapter
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    fun createDummyData() {
        for (i in 0..10) {
            create("Kotlin","author","price","content")
        }
    }

    fun create(title:String,author:String,price:String,content: String) {
        realm.executeTransaction {
            val task = it.createObject(Task::class.java, UUID.randomUUID().toString())
            task.title = title
            task.author = author
            task.price = price
            task.content = content
        }
    }

    //fun readAll(): RealmResults<Task> {
        //return realm.where(Task::class.java).findAll().sort("createdAt", Sort.ASCENDING)
    //}

//    fun update( title:String,author:String,price:String,content: String) {
//        realm.executeTransaction {
//            val task = realm.where(Task::class.java).equalTo("id", "0").findFirst()
//                ?: return@executeTransaction
//            task.title = title
//            task.author = author
//            task.price = price
//            task.content = content
//        }
//    }

    fun update(task: Task, content: String) {
        realm.executeTransaction {
            task.content = content
        }
    }

    fun delete(id: String) {
        realm.executeTransaction {
            val task = realm.where(Task::class.java).equalTo("id", id).findFirst()
                ?: return@executeTransaction
            task.deleteFromRealm()
        }
    }

    fun delete(task: Task) {
        realm.executeTransaction {
            task.deleteFromRealm()
        }
    }

    fun deleteAll() {
        realm.executeTransaction {
            realm.deleteAll()
        }
    }

}