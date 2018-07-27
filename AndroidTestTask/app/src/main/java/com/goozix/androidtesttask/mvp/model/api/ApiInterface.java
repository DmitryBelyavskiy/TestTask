package com.goozix.androidtesttask.mvp.model.api;

import com.goozix.androidtesttask.mvp.model.api.util.APIUtil;
import com.goozix.androidtesttask.mvp.model.user.FoundUsers;
import com.goozix.androidtesttask.mvp.model.user.Repository;
import com.goozix.androidtesttask.mvp.model.user.UpdatedUser;
import com.goozix.androidtesttask.mvp.model.user.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET(APIUtil.USER)
    @Headers("Content-Type: application/json")
    Completable login(
            @Header("Authorization") String token);

    @GET(APIUtil.USERS)
    Single<List<User>> getListUs(@Query("since") int lastUserId);

    @GET(APIUtil.FIND_USERS)
    Single<FoundUsers> getFoundUs(@Query("q") String login, @Query("page") int page);


    @GET(APIUtil.USER)
    @Headers("Content-Type: application/json")
    Single<User> getUserProfile(@Header("Authorization") String token);

    @GET(APIUtil.NO_AUTH_USER_REPOS)
    Single<List<Repository>> getNoAutUserRepositoriesList(@Path("login") String login);

    @GET(APIUtil.AUTH_USER_REPOS)
    Single<List<Repository>> getAutUserRepositoriesList(@Header("Authorization") String token);

    @PATCH(APIUtil.USER)
    Single<User> editUserProfile(@Header("Authorization") String token, @Body UpdatedUser updateInfo);


}
