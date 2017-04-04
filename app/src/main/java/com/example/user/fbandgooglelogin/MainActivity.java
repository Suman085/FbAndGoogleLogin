package com.example.user.fbandgooglelogin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int SIGN_IN_REQUEST_CODE = 1002;
    public static final String ACCOUNT_FACEBOOK = "ACCOUNT_FACEBOOK";
    public static final String ACCOUNT_GOOGLE="ACCOUNT_GOOGLE";
    private LoginButton mLoginButton;
    private FacebookCallback<LoginResult> mCallback;
    private CallbackManager mCallbackManager;
    private ProfileTracker mTracker;
    private AccessTokenTracker mTokenTracker;
    private SignInButton mGoogleSignIn;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(AccessToken.getCurrentAccessToken()!=null){
            gotoWelcomeActivity(Profile.getCurrentProfile(),null);
        }
        mLoginButton=(LoginButton)findViewById(R.id.facebook_login);
        mGoogleSignIn=(SignInButton) findViewById(R.id.google_login);
        mLoginButton.setReadPermissions("email");
        setUpFacebookLogin();
        setUpGoogleLogin();


    }
    private void setUpGoogleLogin() {
        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions)
                .build();
        mGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, SIGN_IN_REQUEST_CODE);
            }
        });
    }

    private void setUpFacebookLogin() {
        mTracker=new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                    if(currentProfile!=null){
                    gotoWelcomeActivity(currentProfile,null);
                    String name=currentProfile.getFirstName();
                    Toast.makeText(MainActivity.this, "Welcome"+name, Toast.LENGTH_SHORT).show();
                }

            }
        };

        mTokenTracker=new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            }
        };
        mCallback=new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                if(loginResult!=null){
                    loginResult.getAccessToken();
                }
               // gotoWelcomeActivity(Profile.getCurrentProfile(),null);

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        };

        mCallbackManager=CallbackManager.Factory.create();
        mLoginButton.registerCallback(mCallbackManager,mCallback);
        mTokenTracker.startTracking();
        mTracker.startTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SIGN_IN_REQUEST_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }else {
            mCallbackManager.onActivityResult(requestCode,resultCode,data);
        }


    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.

            GoogleSignInAccount acct = result.getSignInAccount();
            gotoWelcomeActivity(null, acct);


        } else {
            Toast.makeText(this, "GG_WP", Toast.LENGTH_SHORT).show();;
        }
    }

    private void gotoWelcomeActivity(Profile currentProfile, GoogleSignInAccount account) {
        Intent intent=new Intent(MainActivity.this,WelcomActivity.class);

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
    protected void onStop() {
        super.onStop();
    mTokenTracker.stopTracking();
        mTracker.stopTracking();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
