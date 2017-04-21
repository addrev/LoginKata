package com.tests.loginkata;


import android.os.Handler;

public class SessionApiClient {


    public static final String VALID_USERNAME = "jaimegmail.com";
    public static final String VALID_PASSWORD = "mypass";

    Handler myHandler= new Handler();




    void login(String email, String password, final LoginCallback completionLoginCallback) {
        if (email == null || password == null) {
            postToMainThread(new Runnable() {
                @Override
                public void run() {
                    completionLoginCallback.onError();
                }
            });
            return;
        }
        boolean validLoginData = VALID_USERNAME.equalsIgnoreCase(email) && VALID_PASSWORD.equals(password);
        if (validLoginData) {
            postToMainThread(new Runnable() {
                @Override
                public void run() {
                    completionLoginCallback.onSuccess();
                }
            });
        } else {
            postToMainThread(new Runnable() {
                @Override
                public void run() {
                    completionLoginCallback.onError();
                }
            });
        }
    }

    private void postToMainThread(Runnable runnable) {
        myHandler.post(runnable);
    }

    void logout(final LogoutCallback callback) {
        boolean validTime = (System.currentTimeMillis() / 1000) % 2 == 0;
        if (validTime) {
            postToMainThread(new Runnable() {
                @Override
                public void run() {
                    callback.onSuccess();
                }
            });
        } else {
            postToMainThread(new Runnable() {
                @Override
                public void run() {
                    callback.onError();
                }
            });
        }
    }

    public interface LoginCallback {
        void onSuccess();

        void onError();
    }

    public interface LogoutCallback {
        void onSuccess();

        void onError();
    }
}
