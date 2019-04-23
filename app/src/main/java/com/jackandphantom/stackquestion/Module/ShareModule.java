package com.jackandphantom.stackquestion.Module;

import android.content.Context;

import com.jackandphantom.stackquestion.UI.activities.LoginActivity;
import com.jackandphantom.stackquestion.Utils.SharedPreferenceUtil;

import dagger.Module;
import dagger.Provides;

@Module
public class ShareModule {

    @Provides
    SharedPreferenceUtil provideSharedPreferenceUtil(LoginActivity loginActivity){
        return new SharedPreferenceUtil(loginActivity);
    }
}
