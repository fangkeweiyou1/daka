package com.zhang.daka.config

import com.wushiyi.util.Preference

/**
 * Created by zhangyuncai on 2019/11/8.
 */
val baiducidian_appid = "20191108000354444"
val baiducidian_miyao = "Nhe_4jO7gn9jJs2hwykY"
val baiducidian_url = "https://fanyi-api.baidu.com/"

val danci_type_normal = "normal";//一般
val danci_type_know = "know";//已懂
val danci_type_unknow = "unknow";//未懂

var saveDaoPosition by Preference<Int>("saveDaoPosition", 0)