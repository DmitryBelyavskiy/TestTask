package com.goozix.androidtesttask.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.goozix.androidtesttask.application.MyTestApplication;
import com.goozix.androidtesttask.mvp.view.BaseInfActivityView;

@InjectViewState
public class BaseInfActivityPresenter extends BasePresenter<BaseInfActivityView> {

    public BaseInfActivityPresenter() {
        MyTestApplication.getAppComponent().inject(this);
    }

}
