package com.zhang.daka.ui

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import com.wushiyi.mvp.dddBug

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
        RxPermissions(this).request(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
        val action = intent.action
        /**
         * 收到桌面弹窗中的快捷方式
         */
        dddBug("action:${action}")

    }
}