package com.goozix.androidtesttask.mvp.model.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public final class SharedPrefsModule {

    private static final String PREFERENCES_NAME = "AndroidTestSharedPreferences";
    private static final String PREF_USER_TOKEN = "PREF_USER_TOKEN";

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public SharedPrefsModule(@NonNull Context context) {
        mSharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public void setUserToken(String token) {
        mEditor.putString(PREF_USER_TOKEN, token);
        mEditor.apply();
    }

    public String getUserToken() {
        return mSharedPreferences.getString(PREF_USER_TOKEN, "");
    }

    public void removeUserToken() {
        mEditor.remove(PREF_USER_TOKEN);
        mEditor.apply();
    }

}
