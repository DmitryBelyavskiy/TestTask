package com.goozix.androidtesttask.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.goozix.androidtesttask.application.MyTestApplication;
import com.goozix.androidtesttask.mvp.model.user.User;
import com.goozix.androidtesttask.mvp.view.UserInformationView;
import com.goozix.androidtesttask.ui.detailed_information.UserInformationActivity;

@InjectViewState
public class UserInformationActivityPresenter extends BasePresenter<UserInformationView> {

    User user;

    public UserInformationActivityPresenter(User user) {
        this.user=user;
        MyTestApplication.getAppComponent().inject(this);

    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
       getViewState().loadFragmentProf(user);
    }

}
