package com.zhang.daka.net;

import com.zhang.daka.model.TranslateModel;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by zhangyuncai on 2019/11/8.
 */
public interface ApiService {
    /**
     * q	TEXT	Y	请求翻译query	UTF-8编码
     * from	TEXT	Y	翻译源语言	语言列表(可设置为auto)
     * to	TEXT	Y	译文语言	语言列表(不可设置为auto)
     * appid	TEXT	Y	APP ID	可在管理控制台查看
     * salt	TEXT	Y	随机数
     * sign	TEXT	Y	签名	appid+q+salt+密钥 的MD5值
     * 以下字段仅开通了词典、TTS者需填写
     * tts	STRING	N	是否显示语音合成资源	tts=0显示，tts=1不显示
     * dict	STRING	N	是否显示词典资源	dict=0显示，dict=1不显示
     * 以下字段仅开通“我的术语库”用户需填写
     * action	STRING	N	是否需使用自定义术语干预通用翻译API	1=是，0=否
     *
     * @param map
     * @return
     */
    @GET("api/trans/vip/translate")
    Observable<TranslateModel> translate(@QueryMap Map<String, Object> map);
}
