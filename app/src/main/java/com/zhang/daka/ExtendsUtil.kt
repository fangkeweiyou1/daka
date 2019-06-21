package com.zhang.daka

import android.content.Context
import android.widget.Toast

/**
 * Created by zhangyuncai on 2019/6/21.
 */
lateinit var sContext: Context
val amMinTag="08:30:00"
val amMaxTag="09:30:00"
val pmMinTag="18:30:00"
val pmMaxTag="19:30:00"
val AM="AM"
val PM="PM"

fun toastMsg(content: String) {
    Toast.makeText(sContext, content, Toast.LENGTH_SHORT).show()
}
fun dddBug(content:Any)
{
    println("----------->>>>>>>>-----------$content")
}