package com.lifeistech.l4s.challengeproduct

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_main.authorText
import kotlinx.android.synthetic.main.activity_main.contentText
import kotlinx.android.synthetic.main.activity_main.priceText
import kotlinx.android.synthetic.main.activity_main.titleText

class EditActivity : AppCompatActivity(){
    //val realm = Realm.getDefaultInstance()

    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val bookId = intent.getStringExtra("BOOK_ID") ?: ""
        val task = read(bookId)

        titleText.setText(task?.title)
        authorText.setText(task?.author)
        priceText.setText(task?.price.toString())
        contentText.setText(task?.content)


        changeButton.setOnClickListener {


            val title = titleText.text.toString()
            val author = authorText.text.toString()
            val price = priceText.text.toString().toInt()
            val content = contentText.text.toString()
            update(task, title, author, price, content)
            Log.d("changeButton","tap")



            Log.d("changeButton","save")

            //val realmData = getRealmData()



            val DetailPage = Intent(this, DetailActivity::class.java)

            DetailPage.putExtra("title",title)
            DetailPage.putExtra("author",author)
            DetailPage.putExtra("price",price)
            DetailPage.putExtra("content",content)
            //DetailPage.putExtra("id",realmData?.id)


            startActivity(DetailPage)
        }

//        imageView`.setOnClickListener {// your code here
//            showGallery()
//        }

    }

    fun read(id: String): Task? {
        return realm.where(Task::class.java).equalTo("id", id).findFirst()
    }

    fun update(task: Task?, title: String, author: String, price: Int, content: String) {
        realm.executeTransaction {
            task?.title = title
            task?.author = author
            task?.price = price
            task?.content = content
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }




//    fun save(
//        title: String,
//        author: String,
//        price: String,
//        content: String
//
//    ) {
//        val memo: Task? = read()
//
//        realm.executeTransaction {
//
//            if (memo != null) {
//                memo.title = title
//                memo.author = author
//                memo.price = price.toInt()
//                memo.content = content
//
//            } else {
//
//                val newMemo: Task = it.createObject(Task::class.java, UUID.randomUUID().toString())
//                newMemo.title = title
//                newMemo.author = author
//                newMemo.price = price.toInt()
//                newMemo.content = content
//
//                //newMemo.detail = detail
//            }
//        }


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





//    private var m_uri: Uri? = null
//    private val REQUEST_CHOOSER = 1000
//
//
//    private constructor(parcel: Parcel) : this() {
//        m_uri = parcel.readParcelable(Uri::class.java.classLoader)
//    }
//
//    constructor()
//
//
//    fun onClick(dialog: android.content.DialogInterface?, which: kotlin.Int) {
//        TODO("Not yet implemented")
 //   }



//    private fun showGallery() {
//
//        //カメラの起動Intentの用意
//        val photoName = System.currentTimeMillis().toString() + ".jpg"
//        val contentValues = ContentValues()
//        contentValues.put(MediaStore.Images.Media.TITLE, photoName)
//        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
//        m_uri = this.contentResolver.insert(
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//            contentValues
//        )
//        val intentCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, m_uri)
//
//        // ギャラリー用のIntent作成
//        val intentGallery: Intent
//        if (Build.VERSION.SDK_INT < 19) {
//            intentGallery = Intent(Intent.ACTION_GET_CONTENT)
//            intentGallery.type = "image/*"
//        } else {
//            intentGallery = Intent(Intent.ACTION_OPEN_DOCUMENT)
//            intentGallery.addCategory(Intent.CATEGORY_OPENABLE)
//            intentGallery.type = "image/jpeg"
//        }
//        val intent = Intent.createChooser(intentCamera, "画像の選択")
//        intent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(intentGallery))
//        startActivityForResult(intent, REQUEST_CHOOSER)
//    }
//
//    override fun onActivityResult(
//        requestCode: Int,
//        resultCode: Int,
//        data: Intent?
//    ) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_CHOOSER) {
//            if (resultCode != Activity.RESULT_OK) {
//                // キャンセル時
//                return
//            }
//            val resultUri = (if (data != null) data.data else m_uri)
//                ?: // 取得失敗
//                return
//
//            // ギャラリーへスキャンを促す
//            MediaScannerConnection.scanFile(
//                this,
//                arrayOf(resultUri.path),
//                arrayOf("image/jpeg"),
//                null
//            )
//
//            // 画像を設定
//            imageView.setImageURI(resultUri)
//        }
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeParcelable(m_uri, flags)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<EditActivity> {
//        override fun createFromParcel(parcel: Parcel): EditActivity? {
//            return EditActivity(parcel)
//        }
//
//        override fun newArray(size: Int): Array<EditActivity?> {
//            return arrayOfNulls(size)
//        }
//    }
//
//}