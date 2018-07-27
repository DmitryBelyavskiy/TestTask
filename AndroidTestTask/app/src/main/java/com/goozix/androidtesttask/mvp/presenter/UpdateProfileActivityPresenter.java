package com.goozix.androidtesttask.mvp.presenter;


import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.arellomobile.mvp.InjectViewState;
import com.goozix.androidtesttask.R;
import com.goozix.androidtesttask.application.MyTestApplication;
import com.goozix.androidtesttask.mvp.model.Model;
import com.goozix.androidtesttask.mvp.model.user.UpdatedUser;
import com.goozix.androidtesttask.mvp.model.user.User;
import com.goozix.androidtesttask.mvp.view.UpdateProfileView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class UpdateProfileActivityPresenter extends BasePresenter<UpdateProfileView> {

    @Inject
    Model mModel;

    private User mUser;
    private UpdatedUser mNewUserData;

    public UpdateProfileActivityPresenter(User user) {
        this.mUser = user;
        mNewUserData = new UpdatedUser(user.getName(), user.getCompany(), user.getEmail());
        MyTestApplication.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().showCurrentUserData(mUser);
    }

    public void usernameTextChanged(@NonNull String userName) {
        mNewUserData.setName(userName.trim());
        checkButtonState();
    }

    public void companyTextChanged(@NonNull String company) {
        mNewUserData.setCompany(company.trim());
        checkButtonState();
    }

    public void emailTextChanged(@NonNull String email) {
        mNewUserData.setEmail(email.trim());
        checkButtonState();
    }

    private void checkButtonState() {
        if (validateFields(mNewUserData.getEmail(), mUser.getEmail())) {
            getViewState().enableUpdateUserDataButton(true);
            return;
        }
        if (validateFields(mNewUserData.getName(), mUser.getName())) {
            getViewState().enableUpdateUserDataButton(true);
            return;
        }
        if (validateFields(mNewUserData.getCompany(), mUser.getCompany())) {
            getViewState().enableUpdateUserDataButton(true);
            return;
        }
        getViewState().enableUpdateUserDataButton(false);
    }

    private boolean validateFields(String newString, String oldString) {
        if (newString != null && oldString != null && !newString.equals(oldString)) {
            return true;
        } else if (oldString == null && !TextUtils.isEmpty(newString)) {
            return true;
        }
        return false;
    }

    public void buttonUpdateProfileClicked() {
        unsubscribeOnDestroy(
                mModel.updateUserProfile(mModel.getUserTokenFromPrefs(), mNewUserData)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                getViewState().showProgressBar();
                            }
                        })
                        .subscribe(new Consumer<User>() {
                            @Override
                            public void accept(User user) throws Exception {
                                getViewState().showMessage(R.string.user_profile_updated_successfully);
                                getViewState().showUpdatedProfile(user);
                                getViewState().hideProgressBar();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                getViewState().showMessage(R.string.err_of_edit_profile_user);
                                getViewState().hideProgressBar();
                            }
                        })
        );
    }
}
