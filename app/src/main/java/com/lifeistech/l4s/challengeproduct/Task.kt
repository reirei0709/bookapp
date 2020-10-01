package com.lifeistech.l4s.challengeproduct

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*


open class Task(
    @PrimaryKey open var id: String = UUID.randomUUID().toString(),
    open var imagePath: String = "",
    //open var imageView:Int = 0,
    open var author:String = "",
    open var title:String= "",
    open var price: Int = 0,
    open var content:String = "",
    open var createdAt: Date = Date(System.currentTimeMillis())

//    open var timeAgo:String = foo(createdAt.time)
) : RealmObject()

