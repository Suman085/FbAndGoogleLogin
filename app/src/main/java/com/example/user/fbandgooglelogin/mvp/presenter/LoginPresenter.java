package com.example.user.fbandgooglelogin.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.user.fbandgooglelogin.di.modules.LoginActivityModule;
import com.example.user.fbandgooglelogin.mvp.model.LoginHelper;
import com.example.user.fbandgooglelogin.mvp.view.LoginView;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import javax.inject.Inject;

/**
 * Created by User on 4/5/2017.
 */

public class LoginPresenter implements ILoginPresenter,LoginActivityModule.ResultCallback {
    @Inject
    LoginView mLoginView;
    @Inject
    Context mContext;
    @Inject
    LoginHelper mLoginHelper;

    private static final String TAG=LoginPresenter.class.getSimpleName();
    @Inject
    LoginPresenter(){

    }
    private ProfileTracker mTracker;
    private AccessTokenTracker mTokenTracker;
    private CallbackManager mCallbackManager;
    private FacebookCallback mCallback;


    @Override
    public void setUpFacebookLogin() {

        mTracker=new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                if(currentProfile!=null){
                    Log.e(TAG,"on profile change");
                    mLoginView.gotoWelcomeActivity(currentProfile,null);
                   // gotoWelcomeActivity(currentProfile,null);
//                    String name=currentProfile.getFirstName();
//                    Toast.makeText(LoginActivity.this, "Welcome"+name, Toast.LENGTH_SHORT).show();
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
                Log.e(TAG,"on success");
//                if(loginResult!=null){
//                    loginResult.getAccessToken();
//                }
                mLoginView.gotoWelcomeActivity(Profile.getCurrentProfile(),null);
                // gotoWelcomeActivity(Profile.getCurrentProfile(),null);

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        };
        mCallbackManager= CallbackManager.Factory.create();
        mLoginView.registerCallback(mCallbackManager,mCallback);
        mTokenTracker.startTracking();
        mTracker.startTracking();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onStop() {
        mTokenTracker.stopTracking();
        mTracker.stopTracking();
    }

    @Override
    public void handleSignInResultGoogle(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            mLoginHelper.firebaseAuthWithGoogle(acct,this);
        } else {
           Log.e(TAG,"Error in connection");
            mLoginView.dissmissProgress();
        }
    }

    @Override
    public void onSuccess(GoogleSignInAccount acct) {
        mLoginView.dissmissProgress();
        mLoginView.gotoWelcomeActivity(null,acct);
    }
}
