package com.tests.loginkata;


public interface SessionStorage {

    void saveUser(String email);

    String getUser();

}
