package com.lifeistech.l4s.challengeproduct

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addButton.setOnClickListener {

            val collectionPage = Intent(this,CollectionActivity::class.java)


            startActivity(collectionPage)
        }




    }


}
