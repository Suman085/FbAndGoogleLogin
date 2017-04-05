package com.example.user.fbandgooglelogin.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.user.fbandgooglelogin.R;
import com.example.user.fbandgooglelogin.di.components.DaggerLoginActivityComponent;
import com.example.user.fbandgooglelogin.di.modules.LoginActivityModule;

import com.example.user.fbandgooglelogin.mvp.presenter.LoginPresenter;
import com.example.user.fbandgooglelogin.mvp.view.LoginView;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.Profile;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,LoginView {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final int SIGN_IN_REQUEST_CODE = 1002;
    public static final String ACCOUNT_FACEBOOK = "ACCOUNT_FACEBOOK";
    public static final String ACCOUNT_GOOGLE="ACCOUNT_GOOGLE";
    private LoginButton mLoginButton;
    private SignInButton mGoogleSignIn;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;

    @Inject
    LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resolveDependency();
        if(AccessToken.getCurrentAccessToken()!=null){
            gotoWelcomeActivity(Profile.getCurrentProfile(),null);
        }
        mProgressDialog=new ProgressDialog(this);
        mProgressDialog.setMessage("Please Wait...");
        mLoginButton=(LoginButton)findViewById(R.id.facebook_login);
        mGoogleSignIn=(SignInButton) findViewById(R.id.google_login);
        mLoginButton.setReadPermissions("email");
        mLoginPresenter.setUpFacebookLogin();
        setUpGoogleLogin();


    }

    private void resolveDependency() {
       DaggerLoginActivityComponent.builder()
               .loginActivityModule(new LoginActivityModule(this,this))
               .build()
               .inject(this);
    }

    private void setUpGoogleLogin() {
        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1025791766514-td782ifr4br5us547mavaohgpc6vhl8b.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions)
                .build();
        mGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressDialog.show();
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, SIGN_IN_REQUEST_CODE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SIGN_IN_REQUEST_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            mLoginPresenter.handleSignInResultGoogle(result);

        }else {
            mLoginPresenter.onActivityResult(requestCode,resultCode,data);
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        mLoginPresenter.onStop();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void gotoWelcomeActivity(Profile currentProfile, GoogleSignInAccount account) {
        Intent intent=new Intent(LoginActivity.this,WelcomActivity.class);

        if(account!=null) {
            intent.putExtra(ACCOUNT_GOOGLE, account);
            intent.setFlags(2);
        }else if(currentProfile!=null){
            intent.putExtra(ACCOUNT_FACEBOOK,currentProfile);
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void registerCallback(CallbackManager callbackManager, FacebookCallback callback) {
        mLoginButton.registerCallback(callbackManager,callback);
    }

    @Override
    public void dissmissProgress() {
        mProgressDialog.dismiss();
    }
}
