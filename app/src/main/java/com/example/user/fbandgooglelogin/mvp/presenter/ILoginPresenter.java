package com.example.user.fbandgooglelogin.mvp.presenter;

import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

/**
 * Created by User on 4/5/2017.
 */

public interface ILoginPresenter {
    void setUpFacebookLogin();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onStop();

    void handleSignInResultGoogle(GoogleSignInResult result);

    void onSuccess(GoogleSignInAccount acct);
}
