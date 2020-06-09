package com.me.myhttp.network;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class StringConverter implements Converter<ResponseBody, String> {


    @Override
    public String convert(ResponseBody value) throws IOException {
        return value.string();
    }
}