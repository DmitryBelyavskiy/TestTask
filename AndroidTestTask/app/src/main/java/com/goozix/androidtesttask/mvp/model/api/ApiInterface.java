package com.goozix.androidtesttask.mvp.model.api;

import com.goozix.androidtesttask.mvp.model.api.util.APIUtil;
import com.goozix.androidtesttask.mvp.model.user.FoundUsers;
import com.goozix.androidtesttask.mvp.model.user.Repository;
import com.goozix.androidtesttask.mvp.model.user.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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
    Single<User>getUserProfile(@Header("Authorization")String token);



//    @GET("search/users")
//    Call<FoundUsers> getFoundUsers(@Query("q") String login, @Query("page") int page);
//
//    @GET("users/{login}/repos")
//    Call<List<Repository>> getUserRepositories(@Path("login") String login);

}
