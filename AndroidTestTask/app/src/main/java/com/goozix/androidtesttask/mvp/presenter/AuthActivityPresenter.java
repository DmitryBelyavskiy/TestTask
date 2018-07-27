package com.goozix.androidtesttask.mvp.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.arellomobile.mvp.InjectViewState;
import com.goozix.androidtesttask.R;
import com.goozix.androidtesttask.application.MyTestApplication;
import com.goozix.androidtesttask.mvp.model.Model;
import com.goozix.androidtesttask.mvp.view.AuthActivityView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;

@InjectViewState
public class AuthActivityPresenter extends BasePresenter<AuthActivityView> {

    @Inject
    Model mModel;
    private String mUserName;
    private String mPassword;

    public AuthActivityPresenter() {
        MyTestApplication.getAppComponent().inject(this);
        this.mUserName = "";
        this.mPassword = "";
    }

    public void usernameTextChanged(@NonNull String userName) {
        this.mUserName = userName.trim();
        checkLoginFields();
    }

    public void passwordTextChanged(@NonNull String password) {
        this.mPassword = password.trim();
        checkLoginFields();
    }

    private void checkLoginFields() {

        if (!TextUtils.isEmpty(mUserName) && !TextUtils.isEmpty(mPassword)) {
            getViewState().enableLoginButton(true);
        } else {
            getViewState().enableLoginButton(false);
        }
    }

    public void loginClicked() {
        basicLogin(mUserName, mPassword);
    }

    private void basicLogin(String userName, String password) {

        final String token = Credentials.basic(userName, password);

        unsubscribeOnDestroy(
                mModel.login(token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action() {
                            @Override
                            public void run() throws Exception {
                                mModel.saveUserTokenInPrefs(token);
                                getViewState().onLoginComplete();//если успешно залогинились то запустим след активити
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                getViewState().showMessage(R.string.err_of_auth);
                            }
                        })
        );

    }

}
