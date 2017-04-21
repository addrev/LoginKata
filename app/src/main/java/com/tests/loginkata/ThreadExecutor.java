package com.tests.loginkata;


public class ThreadExecutor implements SessionApiClient.Executor {
    @Override
    public void post(Runnable run) {
        new Thread(run).start();
    }
}
