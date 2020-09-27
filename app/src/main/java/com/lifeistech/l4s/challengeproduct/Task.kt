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
//    open var timeAgo:String = foo(createdAt.time)
) : RealmObject()

fun foo(time: Long): String {
    val diff = System.currentTimeMillis() - time

    val sec = diff / 1000L

    val min = sec / 60L
    if (min == 0L) {
        return "${sec}秒前"
    }

    val hour = min / 60L
    if (hour == 0L) {
        return "${min}分前"
    }

    val day = hour / 24L
    if (day == 0L) {
        return "${hour}時間前"
    }

    return "${day}日"
}






