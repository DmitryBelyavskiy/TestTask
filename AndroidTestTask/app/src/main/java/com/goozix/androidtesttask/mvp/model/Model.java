package com.goozix.androidtesttask.mvp.model;

import android.support.annotation.NonNull;

import com.goozix.androidtesttask.mvp.model.user.Repository;
import com.goozix.androidtesttask.mvp.model.user.UpdatedUser;
import com.goozix.androidtesttask.mvp.model.user.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface Model {

    Completable login(@NonNull String token);

    Single<List<User>> getListUs(int lastUserId, @NonNull String login, int pageCount);

    Single<List<Repository>> getListRepositories(String login);

    Single<User> loadProfAuthUser(@NonNull String token);

    Single<User> updateUserProfile(@NonNull String token, UpdatedUser newUserData);

    void saveUserTokenInPrefs(String token);

    String getUserTokenFromPrefs();

    void removeUserToken();


}
