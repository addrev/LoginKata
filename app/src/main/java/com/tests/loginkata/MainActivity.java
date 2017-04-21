package com.tests.loginkata;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LogInView{

    public static final String USER_LOGGED = "user_logged";
    EditText inputUsername, inputPassword;
    View buttonLogin, buttonLogout;
    SessionApiClient sessionApiClient = new SessionApiClient(new TimeProvider(), new ThreadExecutor(), new MainThreadExecutor());
    boolean isLoggedIn = false;
    SharedPreferences sharedPreferences;
    View progressBar;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("login_data", MODE_PRIVATE);
        setContentView(R.layout.activity_main);
        initVIew();
        presenter=new LoginPresenter(new SessionApiClient(new TimeProvider(), new ThreadExecutor(), new MainThreadExecutor()), new SharedPreferencesSessionStorage(PreferenceManager.getDefaultSharedPreferences(this)));
        presenter.injectView(this);
        presenter.initialize();
    }

    private void initVIew() {
        inputUsername = (EditText) findViewById(R.id.edit_username);
        inputPassword = (EditText) findViewById(R.id.edit_password);
        buttonLogin = findViewById(R.id.button_login);
        buttonLogout = findViewById(R.id.button_logout);
        buttonLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= getInputEmail();
                String password= getInputPassword();
                presenter.onLoginButtonClicked(email,password);


            }
        });
        buttonLogout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onLogoutButtonClicked();
            }
        });
        progressBar = findViewById(R.id.progressBar);
    }


    @Override
    public void hideLogInForm() {
        inputUsername.setVisibility(View.GONE);
        inputPassword.setVisibility(View.GONE);
        buttonLogin.setVisibility(View.GONE);

    }

    @Override
    public void showLogInForm() {
        inputUsername.setVisibility(View.VISIBLE);
        inputPassword.setVisibility(View.VISIBLE);
        buttonLogin.setVisibility(View.VISIBLE);

    }


    @Override
    public void hideLogOutButton() {
        buttonLogout.setVisibility(View.GONE);
    }

    @Override
    public void showLogOutButton() {
        buttonLogout.setVisibility(View.VISIBLE);

    }

    @Override
    public void showError(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void enableButtons() {
        buttonLogin.setClickable(true);
        buttonLogout.setClickable(true);
    }

    @Override
    public void disableButtons() {
        buttonLogin.setClickable(false);
        buttonLogout.setClickable(false);

    }

    public String getInputEmail() {
        return inputUsername.getText().toString();
    }

    public String getInputPassword() {
        return inputPassword.getText().toString();
    }

    @Override
    public void emptyInputUsername() {
        inputUsername.setText(null);
    }

    @Override
    public void emptyInputPassword() {
        inputPassword.setText(null);
    }
}
