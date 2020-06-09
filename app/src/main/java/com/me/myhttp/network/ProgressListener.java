package com.me.myhttp.network;

public abstract class ProgressListener {

    public abstract void onProgress(long total, long progress);
}