package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;



import com.linecorp.linesdk.LoginDelegate;
import com.linecorp.linesdk.LoginListener;
import com.linecorp.linesdk.Scope;
import com.linecorp.linesdk.auth.LineAuthenticationParams;
import com.linecorp.linesdk.auth.LineLoginResult;
import com.linecorp.linesdk.widget.LoginButton;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        ct = getApplicationContext();
        testLine();
        testgoogle();
    }

    private LoginDelegate loginDelegate = LoginDelegate.Factory.create();
    Context ct;
    private void testLine(){
        LoginButton loginButton = findViewById(R.id.line_login_btn);

// if the button is inside a Fragment, this function should be called.
    //    loginButton.setFragment(ct);

        loginButton.setChannelId("1654296545");

// configure whether login process should be done by Line App, or inside WebView.
        loginButton.enableLineAppAuthentication(true);

// set up required scopes and nonce.
        loginButton.setAuthenticationParams(new LineAuthenticationParams.Builder()
                .scopes(Arrays.asList(Scope.PROFILE))
                // .nonce("<a randomly-generated string>") // nonce can be used to improve security
                .build()
        );
        loginButton.setLoginDelegate(loginDelegate);
        loginButton.addLoginListener(new LoginListener() {
            @Override
            public void onLoginSuccess(@NonNull LineLoginResult result) {

            }

            @Override
            public void onLoginFailure(@Nullable LineLoginResult result) {

            }
        });
    }
    private void testgoogle() {
        findViewById(R.id.sign_in_button).setOnClickListener(this);
    }
        @Override
        public void onClick (View v){
            switch (v.getId()) {
                case R.id.sign_in_button:
                    signIn();
                    break;
            }
        }
        private void signIn () {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
}
