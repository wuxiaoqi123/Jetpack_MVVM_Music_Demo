package com.example.music.data.usecase.base;

import android.os.Handler;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class UseCaseThreadPoolScheduler implements UseCaseScheduler {

    public static final int FIXED_POOL_SIZE = 4;
    public static final int TIMEOUT = 30;
    final ThreadPoolExecutor mThreadPoolExecutor;
    private final Handler mHandler = new Handler();

    public UseCaseThreadPoolScheduler() {
        mThreadPoolExecutor = new ThreadPoolExecutor(FIXED_POOL_SIZE, FIXED_POOL_SIZE, TIMEOUT, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    @Override
    public void execute(Runnable runnable) {
        mThreadPoolExecutor.execute(runnable);
    }

    @Override
    public <V extends UseCase.ResponseValue> void notifyResponse(V response, UseCase.UseCaseCallback<V> useCaseCallback) {
        mHandler.post(() -> {
            if (useCaseCallback != null) {
                useCaseCallback.onSuccess(response);
            }
        });
    }

    @Override
    public <V extends UseCase.ResponseValue> void onError(UseCase.UseCaseCallback<V> useCaseCallback) {
        mHandler.post(useCaseCallback::onError);
    }
}
