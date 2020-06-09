package com.me.myhttp.network;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class RxManager {

    private RxManager() {

    }

    private static RxManager ins;

    public static synchronized RxManager getIns() {

        if (ins == null) ins = new RxManager();

        return ins;
    }

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void add(Disposable disposable) {


        compositeDisposable.add(disposable);
    }

    public void cancel() {

        compositeDisposable.dispose();

    }
}
