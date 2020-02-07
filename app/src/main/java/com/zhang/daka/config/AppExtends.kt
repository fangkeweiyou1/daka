package com.zhang.daka.config

import com.wushiyi.mvp.utils.JsonUtil
import com.wushiyi.util.Preference
import com.zhang.daka.model.PlayHistoryModel

var playHistoryModelText by Preference<String>("PlayHistoryModel", "")
fun getPlayHistoryModel(): PlayHistoryModel {
    var playHistoryModel = JsonUtil.jsonToAny<PlayHistoryModel>(playHistoryModelText, PlayHistoryModel::class.java)
    if (playHistoryModel == null) {
        playHistoryModel = PlayHistoryModel();
    }
    return playHistoryModel!!
}

fun setHistoryModel(durationSize: String, objectId: String?) {
    val playHistoryModel = PlayHistoryModel();
    playHistoryModel.durationSize = durationSize;
    playHistoryModel.objectId = objectId;
    playHistoryModelText = JsonUtil.anyToJson(playHistoryModel)
}