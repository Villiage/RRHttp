package com.me.myhttp.network;



import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class MyConverterFactory extends Converter.Factory {



    public static MyConverterFactory create() {

        return new MyConverterFactory();
    }


    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {


        if (String.class.equals(type)) {
            return new StringConverter();
        } else {
            return new GsonConverter(type);
        }


    }


}
