package com.lifeistech.l4s.challengeproduct

import android.app.Activity
import android.app.AlertDialog
import android.app.PendingIntent.getActivity
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.system.Os.read
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_detail.*
import java.nio.file.Files.delete
import java.util.*


class DetailActivity : AppCompatActivity() {
//     val realm: Realm = Realm.getDefaultInstance()

    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    private var taskId: String = ""
    private var task: Task? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //setSupportActionBar(toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

//        //アイコンの設置
//        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
//
//        //リスナー定義
//        toolbar.setNavigationOnClickListener {
//
//            val collectionPage = Intent(this, CollectionActivity::class.java)
//
//            startActivity(collectionPage)

//        }

        taskId = intent.getStringExtra("id") ?: ""

        val realmData = getRealmData()

        Log.d("getkey", "Intent")


//        titleText.text = realmData?.title
//        authorText.text = realmData?.author
//        priceText.text = realmData?.price
//        contentText.text = realmData?.content
//        imageView.url = realmData?.imageView

        Log.d("setText", "setText")

    }

        override fun onResume() {
            super.onResume()
            task = read(taskId)
            updateText()
        }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }









    private fun read(taskId: String): Task? {
        return realm.where(Task::class.java).equalTo("id", taskId).findFirst()
    }



    fun updateText() {

        titleText.text = task?.title
        authorText.text = task?.author
        priceText.text = "¥ ${task?.price}"
        contentText.text = task?.content
    }

    fun getRealmData(): Task? {
        // プライマリーキーをもとに該当のデータを取得
        val id = intent.getStringExtra("id")
        val target = realm.where(Task::class.java)
            .equalTo("id", id)
            .findFirst()

        return target

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.edit -> {
                val EditPage = Intent(this, EditActivity::class.java)
                intent.putExtra("id", task?.id)
                Log.d("id", intent.toString())
                startActivity(EditPage)
            }
            R.id.delete -> {
                val realmData = getRealmData()
                AlertDialog.Builder(this)
                    .setTitle("本の削除")
                    .setMessage(realmData?.title.toString() + "を削除しますか")
                    .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                        // OK button pressed

                        val id = intent.getStringExtra("id")

                        if (id != null) {
                            delete(id)
                        }

                    })

                    .setNegativeButton("Cancel", null)
                    .show()

            }

            else -> super.onOptionsItemSelected(item)

        }
        return true

    }

        private fun delete(id: String) {
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

    }








