package com.goozix.androidtesttask.mvp.model;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.goozix.androidtesttask.application.MyTestApplication;
import com.goozix.androidtesttask.mvp.model.api.ApiInterface;
import com.goozix.androidtesttask.mvp.model.preferences.SharedPrefsModule;
import com.goozix.androidtesttask.mvp.model.user.FoundUsers;
import com.goozix.androidtesttask.mvp.model.user.User;

import java.util.List;

import javax.inject.Inject;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.functions.Function;


public class ModelImpl implements Model {

    @Inject
    ApiInterface mApiInterface;

    @Inject
    SharedPrefsModule mPrefModule;

    public ModelImpl() {
        MyTestApplication.getAppComponent().inject(this);
    }

    @Override
    public Completable login(@NonNull String token) {
        return mApiInterface.login(token);
    }
    @Override
    public Single<User>loadProfAuthUser(@NonNull String token){
        return mApiInterface.getUserProfile(token);
    }

    @Override
    public Single<List<User>> getListUs(int lastUserId, @NonNull String login, int pageCount){
        if(TextUtils.isEmpty(login)){
            return mApiInterface.getListUs(lastUserId);
        }else{
            return mApiInterface.getFoundUs(login, pageCount)
                    .map(new Function<FoundUsers, List<User>>() {
                        @Override
                        public List<User> apply(FoundUsers foundUsers) throws Exception {
                            return foundUsers.getItems();
                        }
                    });
        }
    }

    @Override
    public void saveUserTokenInPrefs(String token) {
        mPrefModule.setUserToken(token);
    }

    @Override
    public String getUserTokenFromPrefs() {
        return mPrefModule.getUserToken();
    }

    @Override
    public void removeUserToken() {
        mPrefModule.removeUserToken();
    }


}
