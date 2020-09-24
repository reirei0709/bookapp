package com.lifeistech.l4s.challengeproduct

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*


open class Task(
    @PrimaryKey open var id: String = UUID.randomUUID().toString(),
    //open var imageView:Int = 0,
    open var author:String = "",
    open var title:String= "",
    open var  price:String = "",
    open var  content:String = "",
    //open var time:Int = 0
    //open var imageId: Int = 0,
    //open var content: String = "",
    open var createdAt: Date = Date(System.currentTimeMillis())
) : RealmObject()

