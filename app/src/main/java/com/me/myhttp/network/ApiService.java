package com.me.myhttp.network;

import com.me.myhttp.WeatherTip;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {


    @GET("cc/json/mobile_tel_segment.htm")
    Observable<String> getTelAddress(@Query("tel") String tel);



    @FormUrlEncoded
    @POST("common?source=xw&weather_type=tips")
    Observable<BaseResponse<WeatherTip>> getWeatherTips(@Field("province") String province, @Field("city") String city);

    @GET
    Observable<ResponseBody> downloadFile(@Url String url);


    @Multipart
    @POST("")
    Call<ResponseBody> upload(@Part MultipartBody.Part part);

}
