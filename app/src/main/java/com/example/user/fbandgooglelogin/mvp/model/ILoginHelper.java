package com.example.user.fbandgooglelogin.mvp.model;

import com.example.user.fbandgooglelogin.di.modules.LoginActivityModule;
import com.example.user.fbandgooglelogin.mvp.view.LoginView;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by User on 4/5/2017.
 */

public interface ILoginHelper  {
    public void firebaseAuthWithGoogle(GoogleSignInAccount acct, LoginActivityModule.ResultCallback callback);
}
