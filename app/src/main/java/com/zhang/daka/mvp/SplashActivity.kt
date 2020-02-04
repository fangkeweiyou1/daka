package com.zhang.daka.mvp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zhang.daka.DakaMainActivity

/**
 * Created by zhangyuncai on 2020/1/7.
 */
class SplashActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //-----------增加这段代码是为了解决release安装包安装后打开app后再按home键再次进入SplashActivity的BUG----------start
        if (!this.isTaskRoot) {
            val intent = intent
            if (intent != null) {
                val action = intent.action
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN == action) {
                    finish()
                    return
                }
            }
        }
        startActivity(Intent(this, DakaMainActivity::class.java))
        finish()
    }
}