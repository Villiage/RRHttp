package com.me.myhttp.network;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonConverter<T> implements Converter<ResponseBody, T> {

    Gson gson;
    Type type;

    GsonConverter(Type type) {
        this.gson = GsonUtil.buildGson();
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {

        String result = value.string();

        return gson.fromJson(result, type);


    }
}