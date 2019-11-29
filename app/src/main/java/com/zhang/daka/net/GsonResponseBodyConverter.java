package com.zhang.daka.net;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by zhangyuncai on 2018/9/11.
 */
public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Application application;
    private final Gson gson;
    private final TypeAdapter<T> adapter;
    private final Type type;

    GsonResponseBodyConverter(Application application, Gson gson, TypeAdapter<T> adapter, Type type) {
        this.application = application;
        this.gson = gson;
        this.adapter = adapter;
        this.type = type;
    }


    /**
     * 返回数据统一处理
     *
     * @param value ResponseBody
     * @return value 返回异常
     * @throws IOException 返回数据统一处理
     */
    @Override
    public T convert(ResponseBody value) throws IOException {

        String response = value.string();
        try {
            return gson.fromJson(response, type);
        } finally {
            value.close();
        }


    }
}