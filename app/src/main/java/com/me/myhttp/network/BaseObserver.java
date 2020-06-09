package com.me.myhttp.network;

import android.widget.Toast;

import com.me.myhttp.MyApp;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        RxManager.getIns().add(d);
    }

    @Override
    public void onNext(@NonNull BaseResponse<T> response) {


        if (response.getStatus() == 200) {
            onSuccess(response.data);
        } else {
            onError(response.status, response.getMessage());
        }


    }

    @Override
    public void onError(@NonNull Throwable e) {

        onError(e.hashCode(), e.getMessage());
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T t);

    public void onError(int code, String msg) {

        Toast.makeText(MyApp.getInstance(), msg, Toast.LENGTH_SHORT).show();

    }
}
