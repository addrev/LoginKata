package com.tests.loginkata;

import com.tests.loginkata.SessionApiClient.LoginCallback;
import com.tests.loginkata.SessionApiClient.LogoutCallback;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SessionApiClientTest {

    SessionApiClient sessionApiClient;
    @Mock
     TimeProvider mockTimeProvider;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        sessionApiClient=new SessionApiClient(mockTimeProvider, new FakeExecutor(),new FakeExecutor());
    }

    @Test
    public void login_fails_if_the_user_is_null() throws Exception {
        LoginCallback mockCompletionLoginCallback = mock(LoginCallback.class);

        sessionApiClient.login(null, "mypass", mockCompletionLoginCallback);

        verify(mockCompletionLoginCallback).onError();
        verify(mockCompletionLoginCallback,never()).onSuccess();
    }
    @Test
    public void login_fails_if_the_user_is_empty() throws Exception {
        LoginCallback mockCompletionLoginCallback = mock(LoginCallback.class);

        sessionApiClient.login("", "mypass", mockCompletionLoginCallback);

        verify(mockCompletionLoginCallback).onError();
        verify(mockCompletionLoginCallback,never()).onSuccess();
    }
    @Test
    public void login_fails_if_the_password_is_empty() throws Exception {
        LoginCallback mockCompletionLoginCallback = mock(LoginCallback.class);

        sessionApiClient.login("jaimegmail.com", "", mockCompletionLoginCallback);

        verify(mockCompletionLoginCallback).onError();
        verify(mockCompletionLoginCallback,never()).onSuccess();
    }
    @Test
    public void login_fails_if_the_password_is_null() throws Exception {
        LoginCallback mockCompletionLoginCallback = mock(LoginCallback.class);

        sessionApiClient.login("jaimegmail.com", null, mockCompletionLoginCallback);

        verify(mockCompletionLoginCallback).onError();
        verify(mockCompletionLoginCallback,never()).onSuccess();
    }
    @Test
    public void login_fails_with_invalid_email_and_password() throws Exception {
        LoginCallback mockCompletionLoginCallback = mock(LoginCallback.class);

        sessionApiClient.login("invalidemail.com", "invalidpass", mockCompletionLoginCallback);

        verify(mockCompletionLoginCallback).onError();
        verify(mockCompletionLoginCallback,never()).onSuccess();
    }

    @Test
    public void login_success_with_correct_email_and_password() throws Exception {
        LoginCallback mockCompletionLoginCallback = mock(LoginCallback.class);

        sessionApiClient.login("jaimegmail.com", "mypass", mockCompletionLoginCallback);

        verify(mockCompletionLoginCallback,never()).onError();
        verify(mockCompletionLoginCallback).onSuccess();
    }

    @Test
    public void logout_fails_with_invalid_time() throws Exception {
        LogoutCallback mockCompletionLoginCallback = mock(LogoutCallback.class);
        when(mockTimeProvider.getCurrentTimeSeconds()).thenReturn(1001l);

        sessionApiClient.logout(mockCompletionLoginCallback);

        verify(mockCompletionLoginCallback).onError();
        verify(mockCompletionLoginCallback,never()).onSuccess();
    }
    @Test
    public void logout_fails_with_valid_time() throws Exception {
        LogoutCallback mockCompletionLoginCallback = mock(LogoutCallback.class);
        when(mockTimeProvider.getCurrentTimeSeconds()).thenReturn(100l);

        sessionApiClient.logout(mockCompletionLoginCallback);

        verify(mockCompletionLoginCallback).onSuccess();
        verify(mockCompletionLoginCallback,never()).onError();
    }
}