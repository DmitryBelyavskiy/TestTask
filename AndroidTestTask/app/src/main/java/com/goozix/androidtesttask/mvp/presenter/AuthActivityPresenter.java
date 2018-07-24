package com.goozix.androidtesttask.mvp.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
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
  private String userName;
  private String password;


  public AuthActivityPresenter() {
      MyTestApplication.getAppComponent().inject(this);
      this.userName = "";
      this.password = "";
  }
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void usernameTextChanged(@NonNull String userName) {
        this.userName = userName.trim();
        checkLoginFields();
    }

    public void passwordTextChanged(@NonNull String password) {
        this.password = password.trim();
        checkLoginFields();
    }
    private void checkLoginFields() {
     /*   if (!email.contains("@")
                || !Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            getViewState().showEmailError(R.string.msg_invalid_email);
            return;
        }*/

        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
            getViewState().enableLoginButton(true);
        } else {
            getViewState().enableLoginButton(false);

        }
    }

    public void loginClicked() {

      basicLogin(userName, password);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    public void basicLogin(String userName, String password){

      final String token= Credentials.basic(userName, password);

        compositeDisposable.add(
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
