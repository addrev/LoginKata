package com.tests.loginkata;


public class FakeExecutor implements SessionApiClient.Executor {
    @Override
    public void post(Runnable runnable) {
        runnable.run();
    }
}
