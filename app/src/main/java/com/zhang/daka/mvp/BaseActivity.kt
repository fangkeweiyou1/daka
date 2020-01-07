package com.zhang.daka.mvp

import android.os.Bundle
import android.os.PersistableBundle
import com.wushiyi.mvp.base.SimpleAppCompatActivity

/**
 * Created by zhangyuncai on 2020/1/7.
 */
abstract class BaseActivity:SimpleAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }
}