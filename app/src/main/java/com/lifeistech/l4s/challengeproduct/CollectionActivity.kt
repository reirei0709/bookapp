package com.lifeistech.l4s.challengeproduct

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_collection.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_data_cell.*
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



        val  DetailPage = Intent(this,DetailActivity::class.java)

        val adapter =
            TaskAdapter(this, taskList, object : TaskAdapter.OnItemClickListener {


                override fun onItemClick(item: Task) {
                    // クリック時の処理

                    Log.d("data",item.title)
                    Log.d("data",item.id)

                    DetailPage.putExtra("id",item.id)




                    startActivity(DetailPage)



                }
            }, true)

        if(recyclerView == null) Log.d("recyclerView", "null")
        else{
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = GridLayoutManager(this, 3)
            recyclerView.adapter = adapter
        }

        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Fabを押しました！", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()

            val MainPage = Intent(this, MainActivity::class.java)

            Log.d("addButton","Intent")


            startActivity(MainPage)
        }


    }



    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    override fun onResume() {
        super.onResume()

        val taskList = readAll()

        //タスクリストが空だったときに"あなたの読んだ本を…"を見えるように
        firstTextView.isVisible = taskList.isEmpty()




    }





//    private fun getRealmData(): Task? {
//        // プライマリーキーをもとに該当のデータを取得
//        val id = intent.getStringExtra("id")
//        val target = realm.where(Task::class.java)
//            .equalTo("id", id)
//            .findFirst()
//
//        return target
//
//
//    }


//    fun createDummyData() {
//        for (i in 0..10) {
//            create("Kotlin","author","price","content")
//        }
//    }



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