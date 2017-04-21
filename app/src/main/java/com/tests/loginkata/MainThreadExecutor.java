package com.tests.loginkata;


import android.os.Handler;
import android.os.Looper;

public class MainThreadExecutor implements SessionApiClient.Executor {

    Handler mainThreadHandler= new Handler(Looper.getMainLooper());
    @Override
    public void post(Runnable runnable) {
        mainThreadHandler.post(runnable);
    }
}
