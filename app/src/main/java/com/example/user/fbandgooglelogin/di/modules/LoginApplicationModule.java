package com.example.user.fbandgooglelogin.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by User on 4/5/2017.
 */


@Module
public class LoginApplicationModule {
    private Context mContext;

    public  LoginApplicationModule (Context context){
        mContext=context;
    }

    @Provides
    @Singleton
    Context providesContext(){
        return mContext;
    }

}
