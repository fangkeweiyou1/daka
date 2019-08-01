package com.zhang.daka

/**
 * Created by zhangyuncai on 2019/6/24.
 */
class Person {
    var list: List<String>? = null
        get() {
            if (field == null) {
                field = mutableListOf()
            }
            return field
        }
}