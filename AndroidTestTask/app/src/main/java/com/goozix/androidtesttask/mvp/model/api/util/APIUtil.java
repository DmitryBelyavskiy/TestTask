package com.goozix.androidtesttask.mvp.model.api.util;

public class APIUtil {
    private static final String BASE_URL_STAGING = "https://api.github.com/";

    public static final String USER = "user";
    public static final String USERS = "users";
    public static final String FIND_USERS = "search/users";
    public static final String NO_AUTH_USER_REPOS = "users/{login}/repos";
    public static final String AUTH_USER_REPOS = "user/repos";

    public static String getBaseUrl() {
        return BASE_URL_STAGING;
    }
}
