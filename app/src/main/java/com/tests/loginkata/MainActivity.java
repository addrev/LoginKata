package com.tests.loginkata;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.tests.loginkata.SessionApiClient.LoginCallback;
import com.tests.loginkata.SessionApiClient.LogoutCallback;

public class MainActivity extends AppCompatActivity {

    public static final String USER_LOGGED = "user_logged";
    EditText inputUsername,inputPassword;
    View buttonLogin,buttonLogout;
    SessionApiClient sessionApiClient= new SessionApiClient();
    boolean isLoggedIn =false;
    SharedPreferences sharedPreferences;
    View progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputUsername= (EditText) findViewById(R.id.edit_username);
        inputPassword= (EditText) findViewById(R.id.edit_password);
        buttonLogin=findViewById(R.id.button_login);
        buttonLogout=findViewById(R.id.button_logout);
        buttonLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= inputUsername.getText().toString();
                String password = inputPassword.getText().toString();
                setLoading(true);
                sessionApiClient.login(email, password, new LoginCallback() {
                    @Override
                    public void onSuccess() {
                        updateLoginStatus(true);
                        setLoading(false);
                    }

                    @Override
                    public void onError() {
                        updateLoginStatus(false);
                        Toast.makeText(getApplicationContext(),"Login failed!! Check your data",Toast.LENGTH_SHORT).show();
                        setLoading(false);

                    }
                });


            }
        });
        buttonLogout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setLoading(true);

                sessionApiClient.logout(new LogoutCallback() {
                        @Override
                        public void onSuccess() {
                            inputPassword.setText(null);
                            inputUsername.setText(null);
                            updateLoginStatus(false);
                            setLoading(false);
                        }

                        @Override
                        public void onError() {
                            updateLoginStatus(true);
                            setLoading(false);
                        }
                    });
            }
        });
        progressBar = findViewById(R.id.progressBar);
        sharedPreferences= getSharedPreferences("login_data",MODE_PRIVATE);
        updateButtons();
        setLoading(false);
        isLoggedIn=sharedPreferences.getBoolean(USER_LOGGED,false);
    }

    private void setLoading(boolean loading){
        if(loading){
            progressBar.setVisibility(View.VISIBLE);
            buttonLogin.setClickable(false);
            buttonLogout.setClickable(false);
        }else {
            progressBar.setVisibility(View.GONE);
            buttonLogin.setClickable(true);
            buttonLogout.setClickable(true);

        }
    }

    private void updateLoginStatus(boolean isLogged) {
        isLoggedIn=isLogged;
        sharedPreferences.edit().putBoolean(USER_LOGGED,isLogged).apply();
        updateButtons();
    }

    private void updateButtons() {
        if(isLoggedIn){
            inputUsername.setVisibility(View.GONE);
            inputPassword.setVisibility(View.GONE);
            buttonLogin.setVisibility(View.GONE);
            buttonLogout.setVisibility(View.VISIBLE);
        }else{
            inputUsername.setVisibility(View.VISIBLE);
            inputPassword.setVisibility(View.VISIBLE);
            buttonLogin.setVisibility(View.VISIBLE);
            buttonLogout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
