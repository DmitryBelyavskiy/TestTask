package com.goozix.androidtesttask.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.goozix.androidtesttask.application.MyTestApplication;
import com.goozix.androidtesttask.mvp.model.user.User;
import com.goozix.androidtesttask.mvp.view.UserInformationView;

@InjectViewState
public class UserInformationActivityPresenter extends BasePresenter<UserInformationView> {

    private User mUser;

    public UserInformationActivityPresenter(User user) {
        this.mUser = user;
        MyTestApplication.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().loadFragmentProf(mUser);
    }

}
