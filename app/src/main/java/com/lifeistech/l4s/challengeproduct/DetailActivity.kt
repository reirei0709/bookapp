package com.lifeistech.l4s.challengeproduct

import android.app.Activity
import android.app.AlertDialog
import android.app.PendingIntent.getActivity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.*


class DetailActivity : AppCompatActivity() {
    val realm = Realm.getDefaultInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val realmData = getRealmData()

//        val  title = intent.getStringExtra("title")
//        val  author = intent.getStringExtra("author")
//        val  price = intent.getStringExtra("price")
//        val  content = intent.getStringExtra("content")

        Log.d("getkey", "Intent")


        titleText.text = realmData?.title
        authorText.text = realmData?.author
        priceText.text = realmData?.price
        contentText.text = realmData?.content

        Log.d("setText", "setText")

        backButton.setOnClickListener {

            val collectionPage = Intent(this, CollectionActivity::class.java)

            startActivity(collectionPage)



        }


        editButton.setOnClickListener {

            val EditPage = Intent(this, EditActivity::class.java)

            startActivity(EditPage)

        }

        deleteButton.setOnClickListener {
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


    }


    private fun getRealmData(): Task? {
        // プライマリーキーをもとに該当のデータを取得
        val id = intent.getStringExtra("id")
        val target = realm.where(Task::class.java)
            .equalTo("id", id)
            .findFirst()

        return target


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


