package com.example.user.fbandgooglelogin.mvp.model;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.user.fbandgooglelogin.di.modules.LoginActivityModule;
import com.example.user.fbandgooglelogin.mvp.presenter.LoginPresenter;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import javax.inject.Inject;

/**
 * Created by User on 4/5/2017.
 */

public class LoginHelper implements ILoginHelper {
    private FirebaseAuth mAuth;
    @Inject
    Context context;
    private static final String TAG=LoginHelper.class.getSimpleName();
    @Inject
    LoginHelper(){
        mAuth=FirebaseAuth.getInstance();
    }

    @Override
    public void firebaseAuthWithGoogle(final GoogleSignInAccount acct, final LoginActivityModule.ResultCallback callback) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                        }else {
                            callback.onSuccess(acct);
                        }
                    }
                });
    }
}
