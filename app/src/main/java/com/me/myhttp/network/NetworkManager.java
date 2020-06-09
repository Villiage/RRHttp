package com.me.myhttp.network;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

public class NetworkManager {

    String baseUrl = "https://my.api.url/";


    private static NetworkManager instance;

    private NetworkManager() {

        initOkClient();
        initRetrofit();
    }

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();

        }

        return instance;
    }

    private Retrofit retrofit;

    private void initRetrofit() {


        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MyConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient).build();


    }

    private ApiService service;

    public ApiService getService() {
        service = retrofit.create(ApiService.class);

        return service;
    }

    public ApiService download(String url, String savePath, ProgressListener listener) {

        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Throwable {

            }
        };
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                okhttp3.Response response = chain.proceed(chain.request());

                return response.newBuilder().body(new ProgressResponseBody(response.body(), listener)).build();

            }
        };
        OkHttpClient client = okHttpClient.newBuilder().addInterceptor(interceptor).build();

        Retrofit newRetrofit = retrofit.newBuilder().client(client).build();
        ApiService apiService = newRetrofit.create(ApiService.class);
        apiService.downloadFile(url)
                .compose(new SchedulerTransformer<>())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        saveFile(responseBody, savePath);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        return retrofit.newBuilder().client(okHttpClient.newBuilder().addInterceptor(interceptor).build()).build().create(ApiService.class);
    }

    public void upload(String name, File file, ProgressListener listener) {


        MediaType type = MediaType.parse("application/otcet-stream");

        RequestBody requestBody = RequestBody.create(type, file);

        ProgressRequestBody progressBody = new ProgressRequestBody(requestBody, listener);


        MultipartBody.Part part = MultipartBody.Part.createFormData(name, file.getName(), progressBody);

        getService().upload(part).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    private OkHttpClient okHttpClient;

    private void initOkClient() {


        okHttpClient = new OkHttpClient.Builder()

                .addInterceptor(new BasicParamInterceptor())
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();


    }

    public void saveFile(ResponseBody body, String savePath) {

        try {

            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(new File(savePath));

                byte[] buffer = new byte[4096];


                while (true) {
                    int read = inputStream.read(buffer);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(buffer, 0, read);


                }
                outputStream.flush();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
