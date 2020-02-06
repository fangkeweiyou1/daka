package com.zhang.daka.kugou

import com.google.gson.annotations.SerializedName

data class KugouLyric(@SerializedName("ugc")
                      val ugc: Int = 0,
                      @SerializedName("proposal")
                      val proposal: String = "",
                      @SerializedName("candidates")
                      val candidates: ArrayList<Candidates>?,
                      @SerializedName("ugccount")
                      val ugccount: Int = 0,
                      @SerializedName("keyword")
                      val keyword: String = "",
                      @SerializedName("info")
                      val info: String = "",
                      @SerializedName("status")
                      val status: Int = 0)

data class KugouLyricInfo(@SerializedName("charset")
                          val charset: String = "",
                          @SerializedName("fmt")
                          val fmt: String = "",
                          @SerializedName("content")
                          val content: String = "",
                          @SerializedName("info")
                          val info: String = "",
                          @SerializedName("status")
                          val status: Int = 0)

/*
{
    "content": "77u\/W2lk
    "info": "OK",
    "status": 200,
    "charset": "utf8",
    "fmt": "lrc"
}
 */

data class Candidates(@SerializedName("song")
                      val song: String = "",
                      @SerializedName("hitlayer")
                      val hitlayer: Int = 0,
                      @SerializedName("singer")
                      val singer: String = "",
                      @SerializedName("language")
                      val language: String = "",
                      @SerializedName("originame")
                      val originame: String = "",
                      @SerializedName("duration")
                      val duration: Int = 0,
                      @SerializedName("transuid")
                      val transuid: String = "",
                      @SerializedName("score")
                      val score: Int = 0,
                      @SerializedName("uid")
                      val uid: String = "",
                      @SerializedName("transname")
                      val transname: String = "",
                      @SerializedName("accesskey")
                      val accesskey: String = "",
                      @SerializedName("adjust")
                      val adjust: Int = 0,
                      @SerializedName("nickname")
                      val nickname: String = "",
                      @SerializedName("soundname")
                      val soundname: String = "",
                      @SerializedName("krctype")
                      val krctype: Int = 0,
                      @SerializedName("origiuid")
                      val origiuid: String = "",
                      @SerializedName("id")
                      val id: String = "",
                      @SerializedName("sounduid")
                      val sounduid: String = "")


/*
{
    "ugccandidates": [],
    "info": "OK",
    "errcode": 200,
    "keyword": "07 偏爱",
    "expire": 86400,
    "companys": "",
    "ugc": 0,
    "has_complete_right": 0,
    "ugccount": 0,
    "errmsg": "OK",
    "proposal": "26125757",
    "status": 200,
    "candidates": [
        {
            "soundname": "",
            "krctype": 2,
            "id": "26125757",
            "originame": "",
            "accesskey": "FE79F7EB04DA8A622BF4477E77E08D4C",
            "parinfo": [],
            "origiuid": "0",
            "score": 60,
            "hitlayer": 7,
            "duration": 215000,
            "adjust": 0,
            "uid": "410927974",
            "song": "偏爱",
            "transuid": "0",
            "transname": "",
            "sounduid": "0",
            "nickname": "",
            "contenttype": 0,
            "singer": "张芸京",
            "language": ""
        },
 */
