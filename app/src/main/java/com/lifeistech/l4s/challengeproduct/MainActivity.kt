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
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity, Parcelable {


    val realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        addButton.setOnClickListener {

            Log.d("addButton","tap")


            val title = titleText.text.toString()
            val author = authorText.text.toString()
            val price: String = priceText.text.toString()
            val content: String = contentText.text.toString()
            save(title, author, price, content)

            Log.d("addButton","save")

//            val DetailPage = Intent(this, DetailActivity::class.java)
//
//            DetailPage.putExtra("title",title)
//            DetailPage.putExtra("author",author)
//            DetailPage.putExtra("price",price)
//            DetailPage.putExtra("content",content)
//
//            Log.d("putExtra","key")

            finish()
        }

        ImageView.setOnClickListener{
            Log.d("imageView","tap")
            showGallery()
        }



    }


    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }


    fun read(): Task? {
        return realm.where(Task::class.java).findFirst()

    }

    fun save(
        title: String,
        author: String,
        price: String,
        content: String

    ) {
        val memo: Task? = read()

        realm.executeTransaction {

            val newMemo: Task = it.createObject(Task::class.java,UUID.randomUUID().toString())
            newMemo.title = title
            newMemo.author = author
            newMemo.price = price
            newMemo.content = content

            //newMemo.detail = detail
        }


    }






    private var m_uri: Uri? = null
    private val REQUEST_CHOOSER = 1000

    private constructor(parcel: Parcel) : this() {
        m_uri = parcel.readParcelable(Uri::class.java.classLoader)
    }

    constructor()


    fun onClick(dialog: android.content.DialogInterface?, which: kotlin.Int) {
        TODO("Not yet implemented")
    }




    private fun showGallery() {

        //カメラの起動Intentの用意
        val photoName = System.currentTimeMillis().toString() + ".jpg"
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.TITLE, photoName)
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        m_uri = this.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
        val intentCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, m_uri)

        // ギャラリー用のIntent作成
        val intentGallery: Intent
        if (Build.VERSION.SDK_INT < 19) {
            intentGallery = Intent(Intent.ACTION_GET_CONTENT)
            intentGallery.type = "image/*"
        } else {
            intentGallery = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intentGallery.addCategory(Intent.CATEGORY_OPENABLE)
            intentGallery.type = "image/jpeg"
        }
        val intent = Intent.createChooser(intentCamera, "画像の選択")
        intent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(intentGallery))
        startActivityForResult(intent, REQUEST_CHOOSER)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHOOSER) {
            if (resultCode != Activity.RESULT_OK) {
                // キャンセル時
                return
            }
            val resultUri = (if (data != null) data.data else m_uri)
                ?: // 取得失敗
                return

            // ギャラリーへスキャンを促す
            MediaScannerConnection.scanFile(
                this,
                arrayOf(resultUri.path),
                arrayOf("image/jpeg"),
                null
            )

            // 画像を設定
            ImageView.setImageURI(resultUri)
        }
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(m_uri, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MainActivity> {
        override fun createFromParcel(parcel: Parcel): MainActivity {
            return MainActivity(parcel)
        }

        override fun newArray(size: Int): Array<MainActivity?> {
            return arrayOfNulls(size)
        }
    }


}

