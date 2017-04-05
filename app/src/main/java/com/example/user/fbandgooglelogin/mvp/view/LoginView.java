package com.example.user.fbandgooglelogin.mvp.view;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.Profile;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by User on 4/5/2017.
 */

public interface LoginView {
    void gotoWelcomeActivity(Profile currentProfile, GoogleSignInAccount account);

    void registerCallback(CallbackManager callbackManager, FacebookCallback callback);

    void dissmissProgress();
}
