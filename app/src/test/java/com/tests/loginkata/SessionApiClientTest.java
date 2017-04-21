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
    public void login_fails_if_the_user_is_empty() throws Exception {
        LoginCallback mockCompletionLoginCallback = mock(LoginCallback.class);

        sessionApiClient.login("", "mypass", mockCompletionLoginCallback);

        verify(mockCompletionLoginCallback).onError();
        verify(mockCompletionLoginCallback,never()).onSuccess();
    }

    @Test
    public void logout() throws Exception {
        LogoutCallback mockCompletionLoginCallback = mock(LogoutCallback.class);
        when(mockTimeProvider.getCurrentTimeSeconds()).thenReturn(1001l).thenReturn(2001l).thenReturn(3000l);

        sessionApiClient.logout(mockCompletionLoginCallback);
        sessionApiClient.logout(mockCompletionLoginCallback);
        sessionApiClient.logout(mockCompletionLoginCallback);

        InOrder inOrder = inOrder(mockCompletionLoginCallback);
        inOrder.verify(mockCompletionLoginCallback,times(2)).onError();
        inOrder.verify(mockCompletionLoginCallback).onSuccess();

    }
}