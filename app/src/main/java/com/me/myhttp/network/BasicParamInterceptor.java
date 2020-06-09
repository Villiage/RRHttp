package com.me.myhttp.network;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

public class BasicParamInterceptor implements Interceptor {

    HashMap<String, String> param;


    public HashMap<String, String> getParam() {

        param = new HashMap<>();

        param.put("time", new Date().toString());

        return param;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        getParam();

        Request request = chain.request();


        if (request.method().equals("GET")) {
            HttpUrl.Builder urlBuilder = request.url().newBuilder();

            for (Map.Entry<String, String> entry : param.entrySet()) {

                urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }


            Request newRequest = request.newBuilder().url(urlBuilder.build()).build();

            return chain.proceed(newRequest);
        } else {

            if (request.body() instanceof FormBody) {
                FormBody oldFormBody = (FormBody) request.body();

                FormBody.Builder newFormBodyBuilder = new FormBody.Builder();


                for (int i = 0; i < oldFormBody.size(); i++) {
                    newFormBodyBuilder.add(oldFormBody.name(i), oldFormBody.value(i));
                }

                for (Map.Entry<String, String> entry : param.entrySet()) {
                    newFormBodyBuilder.add(entry.getKey(), entry.getValue());
                }

                Request newRequest = request.newBuilder().post(newFormBodyBuilder.build()).build();

                return chain.proceed(newRequest);


            } else {

                MultipartBody oldBody = (MultipartBody) request.body();
                MultipartBody.Builder newBodyBuilder = new MultipartBody.Builder();

                for (MultipartBody.Part part : oldBody.parts()) {
                    newBodyBuilder.addPart(part);
                }

                for (Map.Entry<String, String> entry : param.entrySet()) {
                    newBodyBuilder.addFormDataPart(entry.getKey(), entry.getValue());
                }

                Request newRequest = request.newBuilder().post(newBodyBuilder.build()).build();
                return chain.proceed(newRequest);
            }

        }


    }
}
